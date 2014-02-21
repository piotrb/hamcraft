require 'fileutils'
require 'json'

def unzip(file, dst, exclude = [])
  exclude ||= []
  excludes = exclude.map { |i| i = i =~ /\/$/ ? "#{i}*" : i; "-x #{i.inspect}"}.join(" ")
  cmd = "unzip -q -o #{file.inspect} -d #{dst.inspect} #{excludes}"
  system (cmd) || raise("failed")
end

def add_info(lib)
  lib_home = "#{mc_home}/libraries"
  path, name, version = lib["name"].split(':')
  path.gsub!(/\./, '/')

  lib["name"] = name
  lib["version"] = version
  if lib["natives"]
    native = lib["natives"]["osx"]
    lib["path"] = "#{lib_home}/#{path}/#{name}/#{version}/#{name}-#{version}-#{native}.jar"
  else
    lib["path"] = "#{lib_home}/#{path}/#{name}/#{version}/#{name}-#{version}.jar"
  end
end

def handle_lib_extract(lib)
  if File.exist?(lib["path"])
    unzip(lib["path"], "debug/lib/native", lib["extract"]["exclude"])
  else
    # puts "#{name}-#{version} does not exist, can't extract"
  end
end

def handle_lib_copy(lib)
  local_lib_path = "debug/lib/#{lib['name']}-#{lib['version']}.jar"

  if File.exist?(local_lib_path)
    # puts "#{name}-#{version} already exists"
  else
    if File.exist?(lib["path"])
      puts "Copying #{lib["name"]}-#{lib["version"]} ..."
      FileUtils.cp(lib["path"], local_lib_path)
    else
      # puts "#{name}-#{version} does not exist!"
    end
  end
end

def handle_lib(lib)
  add_info(lib)

  if lib["extract"]
    handle_lib_extract(lib)
  else
    handle_lib_copy(lib)
  end

end

def mc_home
  ENV['HOME'] + "/Library/Application Support/minecraft"
end

def version
  "1.6.4-Forge9.11.1.965"
end

def version_json_libs
  version_json = "#{mc_home}/versions/#{version}/#{version}.json"
  json = JSON.parse(File.read(version_json))
  json["libraries"]
end

task :clean do
  FileUtils.rm_rf("api")
end

task :pristine do
  sh "git clean -f -f -d -x"
end

task :prep do
  FileUtils.mkdir_p("debug/lib/native")
  version_json_libs.each { |lib| handle_lib(lib) }
  FileUtils.cp("#{mc_home}/versions/#{version}/#{version}.jar", "debug/minecraft.jar")
end

def download_mod(url)
  basename = File.basename(url)
  unless File.exist?("debug/mods/#{basename}")
    Dir.chdir("debug/mods") do
      sh "wget #{url.inspect}"
    end
  end
end

task :copy_dep_mods do
  FileUtils.mkdir_p("debug/mods")
  download_mod "http://www.curseforge.com/media/files/764/469/CoFHCore-2.0.0.2.jar"
  download_mod "http://www.curseforge.com/media/files/764/471/ThermalExpansion-3.0.0.2.jar"
  download_mod "http://www.chickenbones.craftsaddle.org/Files/New_Versions/1.6.4/CodeChickenCore 0.9.0.7.jar"
  download_mod "http://www.chickenbones.craftsaddle.org/Files/New_Versions/1.6.4/CodeChickenCore 0.9.0.7.jar"
  download_mod "http://www.chickenbones.craftsaddle.org/Files/New_Versions/1.6.4/NotEnoughItems 1.6.1.8.jar"
  download_mod "https://dl.dropboxusercontent.com/u/164771/MC Modding/Waila/Waila_1.4.4b.zip"
  download_mod "http://jenkins.bdew.net/job/bdlib/lastSuccessfulBuild/artifact/build/libs/bdlib-mc164-1.2.0.23.jar"
  download_mod "http://www.mod-buildcraft.com/releases/BuildCraft/4.2.2/buildcraft-A-1.6.4-4.2.2.jar"
end

task :copy_mod do
  FileUtils.mkdir_p("debug/mods")
  Dir["debug/mods/hamcraft-*.jar"].each { |f| File.unlink(f) }
  Dir["build/libs/*.jar"].each { |fn|
    dst = "debug/mods/#{File.basename(fn)}"
    puts "Copying #{fn} -> #{dst}"
    FileUtils.cp(fn, dst)
  }
end

task :run => [:prep, :copy_dep_mods, :copy_mod] do
  Dir.chdir "debug" do
    cmd = []
    cmd << "java"
    cmd << "-Xmx1G"

    classpath = []
    classpath += Dir['lib/*.jar']
    classpath << "./minecraft.jar"

    cmd << "-cp #{classpath.join(':').inspect}"
    cmd << %{-Djava.library.path="./lib/native"}
    cmd << "net.minecraft.launchwrapper.Launch"
    cmd << "-username Player1234"
    cmd << "-version 1.6.4"
    cmd << "-gameDir ."
    cmd << "-assetsDir ./assets"
    cmd << "--width=1280 --height=720"
    cmd << "--tweakClass=cpw.mods.fml.common.launcher.FMLTweaker"

    system cmd.join(' ')
  end
end
