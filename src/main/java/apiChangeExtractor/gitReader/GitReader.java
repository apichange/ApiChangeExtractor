package apiChangeExtractor.gitReader;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;

import apiChangeExtractor.bean.ChangeFile;
import apiChangeExtractor.bean.ChangeLine;

public class GitReader {
	private Git git;
	private Repository repository;
	private RevWalk revWalk;
	private String repositoryPath;
	
	public enum ChangeType {
		ADD,
		DELETE,
		CONTENT
	}
	
	public GitReader(String repositoryPath) {
		this.repositoryPath = repositoryPath;
	}
	
	public void init() throws IOException{
		git = Git.open(new File(repositoryPath));
		repository = git.getRepository();
		revWalk = new RevWalk(repository);
	}
	
	public List<RevCommit> getCommits(){
		List<RevCommit> allCommits = null;
		try {
			Iterable<RevCommit> commits = git.log().call();
			allCommits = new ArrayList<RevCommit>();
//			int count = 0;
			for(RevCommit commit : commits){
//				if(commit.getParentCount()!=1)
//					System.out.println(count+"/"+commit.getName());
				allCommits.add(commit);
//				count++;
			}
		} catch (NoHeadException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return allCommits;
	}
	public List<RevCommit> getCommitsAboutSecurity(){
		List<RevCommit> allCommits = null;
		try {
			Iterable<RevCommit> commits = git.log().call();
			allCommits = new ArrayList<RevCommit>();
			for(RevCommit commit : commits){
				if(isAboutSecurity(commit.getFullMessage()))
					allCommits.add(commit);
			}
		} catch (NoHeadException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return allCommits;
	}
	public List<RevCommit> getCommitsAboutBug(){
		List<RevCommit> allCommits = null;
		try {
			Iterable<RevCommit> commits = git.log().call();
			allCommits = new ArrayList<RevCommit>();
			for(RevCommit commit : commits){
				if(isAboutBug(commit.getFullMessage()))
					allCommits.add(commit);
			}
		} catch (NoHeadException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return allCommits;
	}
	
	public List<RevCommit> getCommitsNotBug(){
		List<RevCommit> allCommits = null;
		try {
			Iterable<RevCommit> commits = git.log().call();
			allCommits = new ArrayList<RevCommit>();
			for(RevCommit commit : commits){
				if(!isAboutBug(commit.getFullMessage()))
					allCommits.add(commit);
			}
		} catch (NoHeadException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return allCommits;
	}
	
	public RevCommit getOneCommit(String sha){
		ObjectId objId;
		try {
			objId = repository.resolve(sha);
			if (objId == null) {
				System.err.println("The commit: " + sha + " does not exist.");
				return null;
			}
			return revWalk.parseCommit(objId);
		} catch (RevisionSyntaxException e) {
			e.printStackTrace();
		} catch (AmbiguousObjectException e) {
			e.printStackTrace();
		} catch (IncorrectObjectTypeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<ChangeFile> getChangeFilesId(RevCommit commit){
    	List<ChangeFile> changeFiles= new ArrayList<ChangeFile>();

		if(commit.getParentCount()!=1) return changeFiles;
		AbstractTreeIterator newTree = prepareTreeParser(commit);
    	AbstractTreeIterator oldTree = prepareTreeParser(commit.getParent(0));
    	List<DiffEntry> diff= null;
		try {
			diff = git.diff().setOldTree(oldTree).setNewTree(newTree).call();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
        
		int modifyCount = 0;
		for (DiffEntry diffEntry : diff) {
			if(DiffEntry.ChangeType.MODIFY.toString().equals(diffEntry.getChangeType().toString())&&diffEntry.getNewPath()!=null&&diffEntry.getNewPath().endsWith(".java")){
				modifyCount++;
				if(isFileToDeal(diffEntry)){
					changeFiles.add(new ChangeFile(diffEntry.getChangeType().toString(), diffEntry.getOldPath(), diffEntry.getNewPath(), 
		        			commit.getName(), (commit.getParents()[0]).getName(), diffEntry.getNewId().toObjectId(), diffEntry.getOldId().toObjectId()));
				}
			}
		} 
		if(modifyCount>5){
			changeFiles.clear();
		}
       return changeFiles;
	}
	
	public List<ChangeFile> getChangeFiles(RevCommit commit){
    	List<ChangeFile> changeFiles= new ArrayList<ChangeFile>();

		if(commit.getParentCount()!=1) return changeFiles;
		AbstractTreeIterator newTree = prepareTreeParser(commit);
    	AbstractTreeIterator oldTree = prepareTreeParser(commit.getParent(0));
    	List<DiffEntry> diff= null;
		try {
			diff = git.diff().setOldTree(oldTree).setNewTree(newTree).call();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
		for (DiffEntry diffEntry : diff) {
			if(diffEntry.getNewPath()!=null&&diffEntry.getNewPath().endsWith(".java")){
				changeFiles.add(new ChangeFile(diffEntry.getChangeType().toString(), diffEntry.getOldPath(), diffEntry.getNewPath(), 
	        			commit.getName(), (commit.getParents()[0]).getName(), diffEntry.getNewId().toObjectId(), diffEntry.getOldId().toObjectId()));

				String fName = "aa"+(new Random()).nextInt(1000);
				while(new File(fName).exists()){
					fName = "aa"+(new Random()).nextInt(1000);
				}
				BufferedOutputStream out =null;
	            try {
	            	out = new BufferedOutputStream(new FileOutputStream(fName));
					DiffFormatter df = new DiffFormatter(out);
	                df.setDiffComparator(RawTextComparator.WS_IGNORE_ALL);
	                df.setRepository(git.getRepository());
	            	df.format(diffEntry);
	            	out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						if(out!=null)
							out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
	            
	            insertAllAddAndDelete(commit.getName(),changeFiles.get(changeFiles.size()-1),fName);
				File file = new File(fName);
				file.delete();
			}
		} 
       return changeFiles;
	}
	public ArrayList<Integer> getRange(String line){
		ArrayList<Integer> ret = new ArrayList<>();
		if(line.length()>2&&"@@".equals(line.substring(0, 2))){
			String pattern = "\\d+";
			Pattern rPattern = Pattern.compile(pattern);
			Matcher matcher = rPattern.matcher(line);
			while(matcher.find()){
				ret.add(Integer.valueOf(matcher.group(0)));
			}
		}
		return ret;
		
	}
	public boolean diffBlockStart(String s){
		if(s == null) return false;
		if(!s.startsWith("@@")) return false;
		if(s.length() <= 4) return false;
		return true;
	}
	private void insertAllAddAndDelete(String name, ChangeFile changeFile, String fName) {
		BufferedReader inputStream =null;
		try {
            inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fName),"UTF-8"));
            ArrayList<Integer> range = new ArrayList<>();
        	String str = inputStream.readLine();
            boolean go = false;
            
            while(str != null && !diffBlockStart(str)){
            	str = inputStream.readLine();
            }
            
			while(str != null){
				ArrayList<Integer> tempRange = getRange(str);
				if(tempRange.size()>0){
					go = false;
					range.clear();
					range.addAll(tempRange);
				}else {
					if(range.size()!=4){
						continue;
					}
					if(range.get(1)==0&&range.get(3)==0){
						go = true;
					}
					if(!go){
						if("+".equals(str.substring(0, 1))){
							changeFile.getChangeLines().add(new ChangeLine(range.get(2), str.replace("\\", "\\\\").replace("\"", "\\\""), ChangeType.ADD.toString()));
							range.set(2, range.get(2)+1);
							range.set(3, range.get(3)-1);
						}else if("-".equals(str.substring(0, 1))){
							changeFile.getChangeLines().add(new ChangeLine(range.get(0), str.replace("\\", "\\\\").replace("\"", "\\\""), ChangeType.DELETE.toString()));
							range.set(0, range.get(0)+1);
							range.set(1, range.get(1)-1);
						}
						else{
							ChangeLine changeLine = new ChangeLine(-1, str.replace("\\", "\\\\").replace("\"", "\\\""), ChangeType.CONTENT.toString(),-1);
							if(range.get(1)!=0){
								changeLine.setOldNum(range.get(0));
								range.set(0, range.get(0)+1);
								range.set(1, range.get(1)-1);
							}
							if(range.get(3)!=0){
								changeLine.setLineNum(range.get(2));
								range.set(2, range.get(2)+1);
								range.set(3, range.get(3)-1);
							}
							changeFile.getChangeLines().add(changeLine);
						}
					}
				}
				str = inputStream.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(inputStream!=null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public AbstractTreeIterator prepareTreeParser(RevCommit commit){
    	try (RevWalk walk = new RevWalk(repository)) {
    		RevCommit temp = walk.parseCommit( commit.getId() );
            RevTree tree = walk.parseTree(temp.getTree().getId());

            CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
            try (ObjectReader oldReader = repository.newObjectReader()) {
                oldTreeParser.reset(oldReader, tree.getId());
            }
            walk.dispose();
            return oldTreeParser;
	    }catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
	
	public void walkCommit(RevCommit commit){
		System.out.println("\ncommit: " + commit.getName());
	    try (TreeWalk treeWalk = new TreeWalk(repository)) {
	        treeWalk.addTree(commit.getTree());
	        treeWalk.setRecursive(true);
	        while (treeWalk.next()) {
	            System.out.println("filename: " + treeWalk.getPathString());
	            ObjectId objectId = treeWalk.getObjectId(0);
	            ObjectLoader loader = repository.open(objectId);
	            loader.copyTo(System.out);
	        }
	    } catch (MissingObjectException e) {
			e.printStackTrace();
		} catch (IncorrectObjectTypeException e) {
			e.printStackTrace();
		} catch (CorruptObjectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
	
	public RevCommit getLastCommit(){
		RevCommit lastCommit = null;
		try {
			Iterable<RevCommit> commits = git.log().setMaxCount(1).call();
			for(RevCommit commit:commits){
				lastCommit = commit;
			}
		} catch (NoHeadException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
		return lastCommit;
	}

	public byte[] getFileContentByCommitId(String commitId, String filePath) {
		if (commitId == null || filePath == null) {
			System.err.println("revisionId or fileName is null");
			return null;
		}
		if (repository == null || git == null || revWalk == null) {
			System.err.println("git repository is null..");
			return null;
		}

		try {
			ObjectId objId = repository.resolve(commitId);
			if (objId == null) {
				System.err.println("The commit: " + commitId + " does not exist.");
				return null;
			}
			RevCommit revCommit = revWalk.parseCommit(objId);
			if (revCommit != null) {
				RevTree revTree = revCommit.getTree();
				TreeWalk treeWalk = TreeWalk.forPath(repository, filePath, revTree);
				ObjectId blobId = treeWalk.getObjectId(0);
				ObjectLoader loader = repository.open(blobId);
				byte[] bytes = loader.getBytes();
				return bytes;
			} else {
				System.err.println("Cannot found file(" + filePath + ") in commit (" + commitId + "): " + revWalk);
			}
		} catch (RevisionSyntaxException e) {
			e.printStackTrace();
		} catch (MissingObjectException e) {
			e.printStackTrace();
		} catch (IncorrectObjectTypeException e) {
			e.printStackTrace();
		} catch (AmbiguousObjectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public byte[] getFileByObjectId(boolean isNewFile, ObjectId blobId) {
		if("0000000000000000000000000000000000000000".equals(blobId.getName())) return new byte[0];
		ObjectLoader loader;
		try {
			loader = repository.open(blobId);
			byte[] bytes = loader.getBytes();
			return bytes;
		} catch (MissingObjectException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean isFileToDeal(DiffEntry entry){
		boolean hasAdd = false;
		boolean hasDel = false;
		boolean hasOther = false;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DiffFormatter diffFormatter = new DiffFormatter(out);
		diffFormatter.setRepository(git.getRepository());
		FileHeader fileHeader;
		int result = 0;
		try {
			fileHeader = diffFormatter.toFileHeader(entry);
			EditList editList = fileHeader.toEditList();
			for(Edit edit : editList){
				switch(edit.getType()){
					case INSERT: 
						result = result+edit.getEndB()-edit.getBeginB();
						hasAdd = true;
						break;
					case DELETE:
						hasDel = true;
						break;
					case REPLACE: 
						result = result+edit.getEndB()-edit.getBeginB();
						hasOther = true;
						break;
					default: break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(hasAdd&&!hasDel&&!hasOther) return false;
		if(!hasAdd&&hasDel&&!hasOther) return false;
		if(result>50) return false;
		return true;
	}
	public boolean isAboutBug(String s){
		String log = s.toLowerCase();
		boolean bugMatch = log.contains("bug")||log.contains("fix")||log.contains("patch");
		if(bugMatch) return true;
		boolean performanceMatch = log.contains("performance")||log.contains("slow")||log.contains("speed")||log.contains("latency")||log.contains("throughput");
		if(performanceMatch) return true;
		
		return isAboutSecurity(s);
	}

	public boolean isAboutSecurity(String s){
		String log = s.toLowerCase();
		boolean match = log.contains("compatibility")||log.contains("compatible")||log.contains("jdk");
		if(match) return true;
		return false;
	}
	public void close(){
		git.close();
	}

	public int getMoveFive() {
		List<RevCommit> list = getCommitsAboutBug();
		if(list==null) return 0;
		int sum = 0;
		for(RevCommit commit:list){
			if(moreFive(commit)) sum++;
		}
		return sum;
	}
	public boolean moreFive(RevCommit commit){
		if(commit.getParentCount()!=1) return false;
		AbstractTreeIterator newTree = prepareTreeParser(commit);
    	AbstractTreeIterator oldTree = prepareTreeParser(commit.getParent(0));
    	List<DiffEntry> diff= null;
		try {
			diff = git.diff().setOldTree(oldTree).setNewTree(newTree).call();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
		int modifyCount = 0;
		for (DiffEntry diffEntry : diff) {
			if(DiffEntry.ChangeType.MODIFY.toString().equals(diffEntry.getChangeType().toString())&&diffEntry.getNewPath()!=null&&diffEntry.getNewPath().endsWith(".java")){
				modifyCount++;
			}
		} 
		if(modifyCount>5){
			return true;
		}
       return false;
	}

	public List<RevCommit> getCommitsFix(Set<String> toDeal) {
		List<RevCommit> allCommits = new ArrayList<RevCommit>();
		for(String s : toDeal){
			RevCommit commit = getOneCommit(s);
			if(commit!=null) allCommits.add(commit);
		}
		return allCommits;
	}
}
