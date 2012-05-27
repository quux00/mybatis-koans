#!/usr/bin/env ruby

# 
# Script to either transfer the completed koans into the main
# src directory or move them out of the src directory back into
# completed-koan dir.
# 
# This can be useful in order to run the completed koans to
# test it on your setup.
#
# This script is not highly intelligent so it if you don't do it
# in the right order it can be destructive, so it's best to save
# any work into git (or your preferred SCM/VCS repo) first.
# That way if you mess up you can just do 'git reset --hard' to
# get back to where you started.
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
# Author: Michael Peterson (midpeter444)

require 'fileutils'

SRCDIR = 'src/net/thornydev/mybatis/koan'
COMPLETED_DIR = 'completed-koans'

# ---[ Koan class ]--- #

class Koan

  attr_reader :name
  attr_reader :db
  
  def initialize(koan)
    if m = koan.match(/(koan1[67])-(\w+)/)
      @name = m[1]
      @db = m[2]
    else
      @name = koan
    end
  end
end

# ---[ Main section ]--- #

def setup_tmp_dir(koan)
  FileUtils.mkdir "tmp" unless Dir.exist? "tmp"
  if Dir.exist? "tmp/#{koan.name}"
    FileUtils.rm_rf "tmp/#{koan.name}/*"
    puts "rm -rf tmp/#{koan.name}/*"
  else
    FileUtils.mkdir "tmp/#{koan.name}"
    puts "mkdir tmp/#{koan.name}"
  end
end

def copy_partials_to_tmp(koan)
  FileUtils.cp_r "#{SRCDIR}/#{koan.name}", "tmp"
  puts "cp -r #{SRCDIR}/#{koan.name}/ tmp"
end

def copy_completed_to_main_koan_area(koan)
  if koan.db
    Dir.glob("#{COMPLETED_DIR}/#{koan.name}/#{koan.db}/*").each do |f|
      puts "cp #{f} #{SRCDIR}/#{koan.name}"
      FileUtils.cp f, "#{SRCDIR}/#{koan.name}"
    end
    
  else
    FileUtils.cp_r "#{COMPLETED_DIR}/#{koan.name}", SRCDIR
    puts "cp -r #{COMPLETED_DIR}/#{koan.name}/ #{SRCDIR}"
  end
end

def move_completed_in_src_back(koan)
  if koan.db
    FileUtils.rm_r "#{COMPLETED_DIR}/#{koan.name}/#{koan.db}"
    FileUtils.mv "#{SRCDIR}/#{koan.name}", "#{COMPLETED_DIR}/#{koan.name}/#{koan.db}"
    puts "mv #{SRCDIR}/#{koan.name} #{COMPLETED_DIR}/#{koan.name}/#{koan.db}"

  else
    FileUtils.rm_r "#{COMPLETED_DIR}/#{koan.name}"
    FileUtils.mv "#{SRCDIR}/#{koan.name}", COMPLETED_DIR
    puts "mv #{SRCDIR}/#{koan.name} #{COMPLETED_DIR}"
  end
end

def copy_tmp_partials_to_src(koan)
  FileUtils.cp_r "tmp/#{koan.name}", SRCDIR
  puts "cp -r tmp/#{koan.name}, #{SRCDIR}"
end

def delete_tmp(koan)
  FileUtils.rm_r "tmp/#{koan.name}"
  puts "rm -r tmp/#{koan.name}"
end

def main(koan, direction)
  if direction == "test"
    setup_tmp_dir(koan)
    copy_partials_to_tmp(koan)
    copy_completed_to_main_koan_area(koan)

  elsif direction == "reset"
    move_completed_in_src_back(koan)
    copy_tmp_partials_to_src(koan)
    delete_tmp(koan)
  end
end

def help
  print <<-END
Usage: test-completed.rb <koan> <test|reset>
where koan is the name of a koan ("koan04")
  test  = move completed into main src dir
  reset = move completed from main src dir back to completed-koan
          dir, restoring the files that were previously in the
          src dir when you ran it in "test" mode

So you should always do it in order of test, then reset
test-completed.rb koan07 test
test-completed.rb koan07 reset

Note: koan16 and 17 are database specific, so you must include a
      suffix indicating which database koan set to test.
      MySQL suffix: -mysql
      PostgreSQL suffix: -pg

  Example: test-completed.rb koan16-pg <test|reset>

  END
end

# ---[ Cmd Line Arg processing functions ]--- #

def ensure_any_args
  if ARGV.size == 0
    $stderr.puts "ERROR: Must provide the name of a koan"
    exit -1
  end
end

def help_if_asked
  if ARGV.first == '-h' || ARGV.first == '--help'
    help
    exit
  end
end

def ensure_koan_arg
  koan = ARGV.first.downcase

  if koan !~ /koan\d\d/i
    $stderr.puts "ERROR: Must provide the name of a koan of form koanNN"
    exit -1
  end

  if koan =~ /koan1[67]/
    if koan !~ /koan1[67]-(pg|mysql)/
      $stderr.puts "ERROR: koan16 and 17 must have a db suffix"
      help
      exit -1
    end
  end
  koan
end

def ensure_direction_arg
  direction = ARGV[1] || "test"
  direction = direction.downcase
  if direction != "test" && direction != "reset"
    $stderr.puts "ERROR: direction must be either 'test' or 'reset'"
    exit -1
  end
  direction
end

if $0 == __FILE__
  # check cmd line args
  ensure_any_args
  help_if_asked
  koan = ensure_koan_arg
  direction = ensure_direction_arg
  
  # invoke script
  main(Koan.new("util"), direction)
  main(Koan.new(koan), direction)
end
