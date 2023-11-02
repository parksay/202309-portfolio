package simple.myboard.myprac.serviceimpl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import simple.myboard.myprac.dao.MemberDao;
import simple.myboard.myprac.service.ArticleService;
import simple.myboard.myprac.service.MemberService;
import simple.myboard.myprac.domain.Member;

public class MemberServiceImpl implements MemberService {
    private MemberDao memberDao;
    private ArticleService articleService;

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public Member getMemberBySeq(int memberSeq) {
        try {
            return this.memberDao.getMemberBySeq(memberSeq);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addMember(Member member) throws DataIntegrityViolationException {
        // null check
        if(member.getUserId() == null || member.getUserPsw() == null || member.getUserName() == null) {
            throw new DataIntegrityViolationException("cannot be null: user_id, user_psw, user_name");
        }
        this.memberDao.insertMember(member);
    }

    public void updateMember(Member member) {
        this.memberDao.updateMember(member);
    }

    public void deleteMemberBySeq(int memberSeq) {
        this.articleService.deleteAllArticleByMemberSeq(memberSeq);
        this.memberDao.deleteMemberBySeq(memberSeq);
    }

    public int getLastIndexMember() {
        return this.memberDao.getLastIndexMember();
    }

}
