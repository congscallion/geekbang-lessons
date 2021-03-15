package slydm.geektimes.training.projects.user.validator.bean.validation;

import javax.validation.GroupSequence;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/13 10:58
 */
@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public interface CompleteInfo {

}
