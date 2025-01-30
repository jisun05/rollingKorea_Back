package history.traveler.rollingkorea.comment.service.Implementation;


import history.traveler.rollingkorea.comment.controller.request.ReplyCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.ReplyEditRequest;
import history.traveler.rollingkorea.comment.domain.Comment;
import history.traveler.rollingkorea.comment.service.ReplyService;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {

//    private final CommentRepository commentRepository;
//    private final ReplyRepository replyRepository;
//    private final UserRepository userRepository;


    @Override
    public void replyCreate(Long commentId, ReplyCreateRequest replyCreateRequest) {
        //User user = getUser();





    }

    @Override
    public void replyEdit(Long replyId, ReplyEditRequest ReplyEditRequest) {

    }

    @Override
    public void replyDelete(Long replyId) {

    }
}
