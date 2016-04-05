# Pre-work - *Twitter Client*

**Twitter Client**.

Submitted by: **Dat Nguyen**

Time spent: **28** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can sign in to Twitter using OAuth login
* [x] User can view the tweets from their home timeline 
      - User should be displayed the username, name, and body for each tweet
      - User should be displayed the relative timestamp for each tweet "8m", "7h"
      - User can view more tweets as they scroll with infinite pagination
* [x] User can compose a new tweet 
      - User can click a “Compose” icon in the Action Bar on the top right
      - User can then enter a new tweet and post this to twitter
      - User is taken back to home timeline with new tweet visible in timeline
* [x] User can switch between Timeline and Mention views using tabs. 
      - User can view their home timeline tweets.
      - User can view the recent mentions of their username.
* [x] User can navigate to view their own profile
      - User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [x] User can click on the profile image in any tweet to see another user's profile
      - User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
      - Profile view should include that user's timeline
* [x] User can infinitely paginate any of these timelines (home, mentions, user) by scrolling to the bottom


The following **optional** features are implemented:
* [x] Advanced: User can click on a tweet to be taken to a "detail view" of that tweet
* [ ] Add pull-to-refresh for popular stream with SwipeRefreshLayout (some errors occur)
* [x] Links in tweets are clickable and will launch the web browser (see autolink)
* [x] Improve the user interface and theme the app to feel "twitter branded"
* [x] User can see embedded image media within the tweet detail view
* [x] Compose activity is replaced with a modal overlay
* [x] Leverage RecyclerView as a replacement for the ListView and ArrayAdapter for all lists of tweets.

* [x] Bonus: Use Parcelable instead of Serializable using the popular Parceler library.
* [x] Bonus: Apply the popular Butterknife annotation library to reduce view boilerplate.

## Video Walkthrough 

Here's a walkthrough of implemented user stories:


GIF created with [LiceCap](http://www.cockos.com/licecap/).


## License

    Copyright [2016] [Dat Nguyen]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
