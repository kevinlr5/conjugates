# Versioning and Releases

This document describes how conjugates is versioned and the process for releasing new versions.

## Branching and Releasing

This repository uses the git flow branching model: http://nvie.com/posts/a-successful-git-branching-model/.

- To release, merge develop into master and tag the merge commit in master. Then push tags.
- If necessary, release branches can be created for testing and bugfixing before the merge to master
- After the merge to master, master must be back-merged into develop
- Command for merging to master: `git merge develop --no-ff`
- Hotfixes are branched from master and then merged back to master. After hotfixing, master is backmerged to develop.

## Versioning

- Versions conform to semantic versioning: http://semver.org/