package life.majiang.community.service;

import life.majiang.community.dto.PageinationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PageinationDTO list(Integer page, Integer size) {
        Integer offset = size*(page-1);



        List<Question> questions = questionMapper.list(offset,size);

        List<QuestionDTO> questionDTOlist = new ArrayList<>();

        PageinationDTO pageinationDTO = new PageinationDTO();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOlist.add(questionDTO);
        }
        pageinationDTO.setQuestions(questionDTOlist);

        Integer totalCount = questionMapper.count();

        pageinationDTO.setPagination(totalCount,page,size);
        return pageinationDTO;
    }
}
