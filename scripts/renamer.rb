#!/usr/bin/env ruby

require 'fileutils'

def main(from, to)
  Dir.glob('*').each do |e|
    e.chomp!
    next if e == $0
    newname = e.gsub(/#{from}/, to)
    unless e == newname
      FileUtils.mv(e, newname)
      # puts "FileUtils.mv(#{e}, #{newname})"
    end
  end
end

def help
  puts "renamer.rb <from-tok> <to-tok>"
  puts "  Renames all files in the current directory that have <from-tok>"
  puts "  in the filename and changes that token to the <to-tok>."
  puts "  Example: renamer.rb 13 14"
  puts '  Will rename all files with a "13" in the filename and replace it'
  puts '  with a "14"'
  puts "\nNote: this script does NOT act recursively - it only renames what"
  puts "it finds in the current directory"
  exit
end

if $0 == __FILE__
  from = ARGV[0] || help
  to = ARGV[1] || help
  main(from, to)
end
