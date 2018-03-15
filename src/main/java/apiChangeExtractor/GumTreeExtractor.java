package apiChangeExtractor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.eclipse.jgit.revwalk.RevCommit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apiChangeExtractor.bean.ChangeFile;
import apiChangeExtractor.bean.RenameList;
import apiChangeExtractor.evaluation.ETimer;
import apiChangeExtractor.gitReader.GitReader;
import apiChangeExtractor.gumtreeParser.GumTreeDiffParser;
import apiChangeExtractor.mybatis.bean.ExistRepository;
import apiChangeExtractor.mybatis.bean.Repository;
import apiChangeExtractor.util.FileUtils;
import apiChangeExtractor.util.PathUtils;

import com.github.gumtreediff.actions.model.Action;

public class GumTreeExtractor {
	private static final Logger logger = LoggerFactory.getLogger(GumTreeExtractor.class);
	
	private GitReader gitReader;
	private int repositoryId;
	private String repositoryPath;
	private String webRoot;
	
	public GumTreeExtractor(String path, int repositoryId){
		repositoryPath = path;
		gitReader = new GitReader(repositoryPath);
		try {
			gitReader.init();
		} catch (IOException e) {
			gitReader=null;
		}
		this.repositoryId = repositoryId;
		this.webRoot = null;
	}
	public GumTreeExtractor(Repository repository){
		repositoryPath = repository.getAddress();
		gitReader = new GitReader(repositoryPath);
		try {
			gitReader.init();
		} catch (IOException e) {
			gitReader=null;
		}
		this.repositoryId = repository.getRepositoryId();
		this.webRoot = repository.getWebsite()+"/commit/";
	}
	public GumTreeExtractor(ExistRepository repository){
		repositoryPath = repository.getAddress();
		gitReader = new GitReader(repositoryPath);
		try {
			gitReader.init();
		} catch (IOException e) {
			gitReader=null;
		}
		this.repositoryId = repository.getRepositoryId();
		this.webRoot = repository.getWebsite()+"/commit/";
	}
	
	public void extractActions(){
		ETimer filterTimer = new ETimer();
		ETimer diffTimer = new ETimer();
		ETimer matchTimer = new ETimer();
		ETimer countTimer = new ETimer();
		ETimer totalTimer = new ETimer();
		
		if(gitReader==null){
			logger.warn(repositoryId + " : " + repositoryPath +" repository not found!");
			return;
		}
		
		totalTimer.startTimer();
		
		filterTimer.startTimer();
		List<RevCommit> commits = gitReader.getCommitsAboutBug();
		filterTimer.endTimer();
		if(commits==null){
			logger.warn(repositoryId + " : " + repositoryPath +" commits not found!");
			return;
		}
		
		String userDirPath = System.getProperty("user.dir");
		String tempDirPath = userDirPath + "/" + UUID.randomUUID().toString();
		File tempDir = new File(tempDirPath);
		tempDir.mkdirs();
		
		for(int i = 0; i < commits.size(); i++){
			if(commits.get(i).getParents().length==0) continue;
			
			List<ChangeFile> changeFiles = gitReader.getChangeFilesId(commits.get(i));
			for(ChangeFile changeFile : changeFiles){
				byte[] newContent = gitReader.getFileByObjectId(true,changeFile.getNewBlobId());
				byte[] oldContent = gitReader.getFileByObjectId(false,changeFile.getOldBlobId());
				String randomString = PathUtils.getUnitName(changeFile.getNewPath());
				File newFile = FileUtils.writeBytesToFile(newContent, tempDirPath, randomString + ".v1");
				File oldFile = FileUtils.writeBytesToFile(oldContent, tempDirPath, randomString + ".v2");
				
				if(newFile.length()/1048576>1) continue;
				
				diffTimer.startTimer();
				GumTreeDiffParser diff = new GumTreeDiffParser(oldFile, newFile);
				try{
					diff.init();
				}catch(Exception e){
					newFile.delete();
					oldFile.delete();
					continue;
				}
				diffTimer.endTimer();
				
				matchTimer.startTimer();
				List<Action> actions = diff.getActions();
				ActionParser parser = new ActionParser(commits.get(i), diff, webRoot, changeFile, repositoryId, countTimer);
				RenameList rl = new RenameList();
				for(Action a:actions)
				{
					rl = parser.parseOneAction(a,rl);
				}
				matchTimer.endTimer();
				
				newFile.delete();
				oldFile.delete();
			}
		}
		tempDir.delete();
		totalTimer.endTimer();
		
//		ProjectInfo info = new ProjectInfo();
//		info.setRepositoryId(repositoryId);
//		info.setAordBugCommits(usedCommits);
//		info.setFilterTime((int)filterTimer.getTotalTime());
//		info.setDiffTime((int)diffTimer.getTotalTime());
//		info.setMatchTime((int)matchTimer.getTotalTime());
//		info.setCountTime((int)countTimer.getTotalTime());
//		info.setTotalTime((int)totalTimer.getTotalTime());
//		ProjectInfoDao.getInstance().updateProjectInfo(info);
		logger.warn("repository "+repositoryId+" end extractor.");
	}
	
	public void clearSource(){
		if(gitReader!=null) gitReader.close();
	}
}
