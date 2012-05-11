#!/usr/bin/env ruby

# 
# Script to either transfer the completed koans into the main
# src directory or move them out of the src directory back into
# completed-koan dir.
# 
# This can be useful in order to run the completed koans to
# test it on your setup.
#
# This script is not highly intelligent so it could mess things
# up if you don't do it in the right order, so it's best to save
# any work into git (or your preferred SCM/VCS repo) first.
#
# Usage:
# test-completed.rb <koan> <test|reset>
# where koan is the name of a koan ("koan04")
#   test  = move completed into main src dir
#   reset = move completed from main src dir back to completed-koan
#           dir, restoring the files that were previously in the
#           src dir when you ran it in "test" mode
# 
# So you should always do it in order of test, then reset
# test-completed.rb koan07 test
# test-completed.rb koan07 reset
# 

require 'fileutils'

srcdir = 'src/net/thornydev/mybatis/koan'
completed_dir = 'completed-koans'

def setup_tmp_dir(koan)
  FileUtils.mkdir "tmp" unless Dir.exist? "tmp"
  if Dir.exist? "tmp/#{koan}"
#    FileUtils.rm_rf "tmp/#{koan}/*"
    puts "rm -rf tmp/#{koan}/*"
  else
#    FileUtils.mkdir "tmp/#{koan}"
    puts "mkdir tmp/#{koan}"
  end
end

def copy_partials_to_tmp(koan)
#  FileUtils.cp_r "#{srcdir}/#{koan}/* tmp/#{koan}"
  puts "cp -r #{srcdir}/#{koan}/* tmp/#{koan}"
end

def copy_completed_to_main_koan_area
#  FileUtils.cp_r "#{completed_dir}/#{koan}/* #{srcdir}/#{koan}"
  puts "cp -r #{completed_dir}/#{koan}/* #{srcdir}/#{koan}"
end

def main(koan, direction)
  if direction == "test"
    setup_tmp_dir(koan)
    copy_partials_to_tmp(koan)
    copy_completed_to_main_koan_area
  elsif direction == "reset"
    puts "...reset..."
  end
end

if $0 == __FILE__
  if ARGV.size == 0
    $stderr.puts "ERROR: Must provide the name of a koan"
    exit -1
  elsif ARGV.first !~ /koan\d\d/i
    $stderr.puts "ERROR: Must provide the name of a koan of form koanNN"
    exit -1
  end
  koan = ARGV.first.downcase
  direction = ARGV[1] || "test"
  direction = direction.downcase
  if direction != "test" || direction != "reset"
    $stderr.puts "ERROR: direction must be either 'test' or 'reset'"
    exit -1
  end
  main(koan, direction)
end
