package com.lightning.jpipeworks.lcjei;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.lightning.jpipeworks.resources.LoadableResource;

import github.lightningcreations.lcjei.resources.Resource;
import github.lightningcreations.lcjei.resources.ResourceSet;

public class PipeworksResourceController {
	
	private static ResourceSet<String> resourceSet;
	
	private static final class DefaultPipeworksResource implements Resource<String>{
		private Path target;
		private String key;
		
		DefaultPipeworksResource(Path target,String key){
			this.target = target;
			this.key = key;
		}
		
		@Override
		public InputStream getReadStream() {
			// TODO Auto-generated method stub
			try {
				return Files.newInputStream(target);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public SeekableByteChannel getReadChannel() {
			try {
				return Files.newByteChannel(target, StandardOpenOption.READ);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public String getKey() {
			// TODO Auto-generated method stub
			return key;
		}
		
	}
	
	public static final class DefaultPipeworksResourceSet implements ResourceSet<String>{
		private Path root;
		private Set<Path> enumeratedKeys = new TreeSet<>();
		private Map<String,Resource<String>> found = new TreeMap<>();
		private boolean hasEnumerated;
		private DefaultPipeworksResourceSet() {
			this(Paths.get(""));
		}
		private DefaultPipeworksResourceSet(Path root) {
			this.root = root;
			this.reload();
		}
		
		@Override
		public Optional<Resource<String>> getResource(String key) {
			if(found.containsKey(key))
				return Optional.of(found.get(key));
			Path target = root.resolve(key);
			if(Files.isReadable(target)){
				DefaultPipeworksResource res = new DefaultPipeworksResource(target,key);
				return Optional.of(res);
			}else
				return Optional.empty();
		}

		@Override
		public Stream<String> keys() {
			if(!hasEnumerated)
				enumerate(root,new LinkedList<>());
			return enumeratedKeys.stream().map(p->p.relativize(root)).map(Path::toString);
		}
		
		/**
		 * Implements a depth-first enumeration of all non-directories at a given parent and all of its subdirectories.
		 */
		private void enumerate(Path root,Deque<Path> pathStack) {
			pathStack.push(root);
			try {
				for(Path p:Files.newDirectoryStream(root)) {
					if(Files.isDirectory(p))
						enumerate(p,pathStack);
					else if(Files.isReadable(p))
						enumeratedKeys.add(p);
				}
			}catch(IOException e) {
			}
			pathStack.pop();
		}
		
		@Override
		public void reload() {
			enumeratedKeys.clear();
			found.clear();
			this.hasEnumerated = false;
		}
		
	}
	
	static {
		setPipeworksResourceSet(new DefaultPipeworksResourceSet());
	}
	
	public static void setPipeworksResourceSet(ResourceSet<String> set) {
		resourceSet = set;
		LoadableResource.setLookupFunction(getPipeworksLookupFn(set));
	}
	
	public static void init() {} //Call Static Initializer

	
	private static Function<String,Optional<Supplier<InputStream>>> getPipeworksLookupFn(ResourceSet<String> res){
		return s->res.getResource(s).map(str->str::getReadStream);
	}
	
	public static ResourceSet<String> getPipeworksResourceSet(){
		return resourceSet;
	}
	

}
