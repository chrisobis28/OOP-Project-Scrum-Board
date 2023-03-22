# Code Contributions and Code Reviews

#### Focused Commits

Grade: Sufficient!

Feedback: The repository has a decent amount of commits, but the team should overall have a higher number of commits. Most commits only affect a small number of files and aggregate a coherent change to the system. Some commits are extremely large, this is not a good practice (https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-51/-/commit/247b618fef0154916eb364fabd729c4c2b12c365). Most commit messages are concise one liners, which clearly summarize the change.

#### Isolation

Grade: Good!

Feedback: The group uses feature branches/merge requests to isolate individual features during development, which is great! The degree of focus on each merge request is quite high, I managed to find one merge request that had a large scope (https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-51/-/merge_requests/13/diffs). Some of you did not contribute a lot via merge requests, please keep in mind this is very important!


#### Reviewability

Grade: Very good!

Feedback: Most merge requests have a clear focus that becomes clear from the title and/or description. Merge requests contain code changes and only a low number of formatting changes. Merge requests cover a small amount of commits, this is great! The changes is MR's are coherent and relate to eachother. Merges happen usually not after a long time after the creation of the branch.


#### Code Reviews

Grade: Insufficient!

Feedback: Most merge reviews, no matter the size, are not properly reviewed. This is not a good practice. Each merge request should be reviewed, and the amount of discussion raised with each merge request should correlate with the size of the merge request. This merge request has (https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-51/-/merge_requests/13) no reviews at all. The reviews that are present are too shallow (https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-51/-/merge_requests/18 and https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-51/-/merge_requests/11). Everyone should contribute to code reviews, and the discussion should be constructive an goal oriented, leading to iterative improvements of the code.


#### Build Server

Grade: Very good!

Feedback: The team has not selected enough checkstyle rules. Please remember to add more checkstyle rules to your checkstyle.xml file! Build times are reasonable. Failing builds are fixed directly after them failing, which is great! Builds don't fail on main, well done! The team also has commited and pushed frequently and most builds pass.