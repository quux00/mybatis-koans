## Utility scripts

This directory provides utility scripts. You must have Ruby installed to use them. I use Ruby 1.9.2. They may not work with earlier versions, since I haven't tested that.

### test-completed.rb

Run `scripts/test-completed.rb -h` to see usage info.

I use this to test the completed koans to make sure they are working.  Right now it works on one koan at a time.

**Caution**: if you don't use this script correctly it can be destructive, so:

1. make sure you read how to use it
2. save any local changes you've already made to git or your version control system of choice, so you can revert / reset if you run it incorrectly

**Example usage:**

To test whether completed koan 11 works on my set, I would do:

    scripts/test-completed.rb koan11 test
    ant koan11
    scripts/test-completed.rb koan11 reset

The "test" directive saves your current koan11 files to a tmp directory and then copies over the koan11 files from the `completed-koans/koan11` directory.  You then run the koan with ant and finally put the respective file sets back with the "reset" directive.  If you run `reset` without running `test` first, it will do bad things, so read the Caution above again before using.

Note: running "test" and "reset" does not affect your `src/net/thornydev/mybatis/koan/config.properties` file, so once that is set to the right database you can leave it alone.

Note: koans 16 and 17 solutions are database specific, so you have to add a suffix to specify which one you want like so:

    scripts/test-completed.rb koan17-mysql test
    ant koan17   
    scripts/test-completed.rb koan17-mysql reset



### renamer.rb

This script changes koan numbers in case we need to insert a new koan between existing ones. You will not need to use this script.
