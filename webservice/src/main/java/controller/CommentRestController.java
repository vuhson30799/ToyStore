package controller;

import JSON.Comment;
import JSON.FavoriteToy;
import model.Account;
import model.Rating;
import model.Toy;
import org.joda.time.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AccountService;
import service.RatingService;
import service.ToyService;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentRestController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ToyService toyService;

    @Autowired
    private AccountService accountService;

    private String timeAgo(Timestamp time) {
        DateTime dateTime = new DateTime(time.getTime());
        DateTime currentTime = new DateTime(System.currentTimeMillis());
        int year = Years.yearsBetween(dateTime, currentTime).getYears();
        if (year != 0) {
            if (year == 1) {
                return "1 year ago";
            }
            return year + " years ago";
        } else {
            int month = Months.monthsBetween(dateTime, currentTime).getMonths();
            if (month != 0) {
                if (month == 1) {
                    return "1 month ago";
                }
                return month + " months ago";
            } else {
                int week = Weeks.weeksBetween(dateTime, currentTime).getWeeks();
                if (week != 0) {
                    if (week == 1) {
                        return "1 week ago";
                    }
                    return week + " weeks ago";
                } else {
                    int day = Days.daysBetween(dateTime, currentTime).getDays();
                    if (day != 0) {
                        if (day == 1) {
                            return "1 day ago";
                        }
                        return day + " days ago";
                    } else {
                        int hour = Hours.hoursBetween(dateTime, currentTime).getHours();
                        if (hour != 0) {
                            if (hour == 1) {
                                return "1 hour ago";
                            }
                            return hour + " hours ago";
                        } else {
                            int minutes = Minutes.minutesBetween(dateTime, currentTime).getMinutes();
                            if (minutes != 0) {
                                if (minutes == 1) {
                                    return "1 minute ago";
                                }
                                return minutes + " minutes ago";
                            } else {
                                int second = Seconds.secondsBetween(dateTime, currentTime).getSeconds();
                                if (second != 0) {
                                    if (second == 1) {
                                        return "1 second ago";
                                    }
                                    return second + " seconds ago";
                                } else {
                                    return "Just finished";
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private List<Long> parentIds;

    private void findParentIds(Long parentId, Long toyId) {

        for (Rating rating : ratingService.findAllByParentIdAndToy_Id(parentId, toyId)) {

            parentIds.add(rating.getId());
            findParentIds(rating.getId(), toyId);

        }
    }

    private void setRootId(List<Comment> comments, List<Comment> commentsAll, Long constId, Long id) {

        for (Comment comment : commentsAll) {
            if (comment.getRatingId() == id) {
                if (comment.getParentId() == 0) {
                    for (Comment cmt : comments) {
                        if (cmt.getRatingId() == constId) {
                            cmt.setRootId(id);
                            return;
                        }
                    }
                } else {
                    setRootId(comments, commentsAll, constId, comment.getParentId());
                }
            }
        }
    }

    // get comments
    @RequestMapping(value = "/comments/{parentId}/{toyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> listComments(@PathVariable Long parentId, @PathVariable Long toyId) {

        List<Rating> ratings;

        if (parentId == 0) {
            ratings = ratingService.findAllByParentIdAndToy_Id(parentId, toyId);
        } else {

            parentIds = new ArrayList<>();
            parentIds.add(parentId);
            findParentIds(parentId, toyId);

            ratings = ratingService.findAllByParentIdsAndToy_Id(parentIds, toyId);
        }

        List<Rating> allRatings = ratingService.findAllByToyId(toyId);
        if (ratings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Comment> comments = new ArrayList<>();
        Long qty;
        for (Rating rating : ratings) {

            Long pID = rating.getId();
            qty = 0L;

            for (Rating cmt : allRatings) {

                parentIds = new ArrayList<>();
                parentIds.add(pID);
                findParentIds(pID, toyId);

                for (Long i : parentIds) {
                    if (cmt.getParentId() == i) {
                        qty++;
                        break;
                    }
                }

            }

            Comment comment = new Comment();

            comment.setRatingId(rating.getId());
            comment.setUsername(rating.getAccount().getUsername());
            comment.setParentId(rating.getParentId());
            comment.setRatingStar(rating.getRatingStar());
            comment.setComment(rating.getComment());
            comment.setTimeAgo(timeAgo(rating.getPostDate()));
            comment.setNameUser(rating.getAccount().getName());
            comment.setToyId(rating.getToy().getId());
            comment.setReplyQty(qty);

            if (rating.getParentId() != 0) {
                comment.setParentName(ratingService.findById(rating.getParentId()).getAccount().getName());
            }

            comments.add(comment);
        }

        // set root id
        List<Rating> ratingsTemp = ratingService.findAllByToyId(toyId);
        List<Comment> commentsTemp = new ArrayList<>();

        for (Rating rating : ratingsTemp) {
            commentsTemp.add(new Comment(rating.getId(), rating.getParentId()));
        }

        for (Comment comment : comments) {
            setRootId(comments, commentsTemp, comment.getRatingId(), comment.getRatingId());
        }

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // create comment
    @RequestMapping(value = "/comments/", method = RequestMethod.POST)
    public ResponseEntity<Void> createComment(@RequestBody Comment comment, Principal principal) {

        if (principal == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        if (comment.getRatingStar() == null) {
            comment.setRatingStar(0L);
        }

        Account account = accountService.findAccountByUsername(comment.getUsername());

        Rating rating = new Rating(
                comment.getRatingStar(),
                comment.getComment(),
                comment.getParentId(),
                new Timestamp(System.currentTimeMillis()),
                toyService.findById(comment.getToyId()),
                account
        );

        ratingService.save(rating);

        if (comment.getRatingStar() != ratingService.findAllByAccount_UsernameAndToy_Id(comment.getUsername(), comment.getToyId()).get(0).getRatingStar()) {
            ratingService.updateRatingStar(comment.getToyId(), account.getId(), comment.getRatingStar());
        }

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    // add to favorite list
    @RequestMapping(value = "/favorites/", method = RequestMethod.POST)
    public ResponseEntity<Void> createFavoriteToy(@RequestBody FavoriteToy favoriteToy, Principal principal) {

        Toy toy = toyService.findById(favoriteToy.getToyId());

        if (principal == null || toy == null) {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

        Account account = accountService.findAccountByUsername(principal.getName());

        if (!account.containToy(toy.getId())) {

            account.addToy(toy);
            toy.addAccount(account);

            accountService.save(account);
            toyService.save(toy);

        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);

    }

}