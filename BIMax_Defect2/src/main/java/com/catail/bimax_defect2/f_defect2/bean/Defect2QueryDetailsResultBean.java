package com.catail.bimax_defect2.f_defect2.bean;

import java.util.List;

public class Defect2QueryDetailsResultBean {
    private String errstr_cn;
    private String errstr;
    private ResultBean result;
    private Integer errno;

    public String getErrstr_cn() {
        return errstr_cn;
    }

    public void setErrstr_cn(String errstr_cn) {
        this.errstr_cn = errstr_cn;
    }

    public String getErrstr() {
        return errstr;
    }

    public void setErrstr(String errstr) {
        this.errstr = errstr;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public static class ResultBean {
        private String drawing_id;
        private String apply_user_id;
        private String category_id;
        private String defect_no;
        private String status;
        private String check_id;
        private String drawing_position;
        private String flow_node;
        private String secondary_region;
        private String discipline_id;
        private List<ActionBean> action;
        private String pic;
        private String remarks;
        private List<DetailBean> detail;
        private String drawing_pic_path;
        private String contractor_name;
        private String deadline_time;
        private String sub_con;
        private String category;
        private String project_id;
        private String block;
        private String program_name;
        private String trade;
        private String trade_id;
        private String urgent_flag;
        private String discipline;
        private String location;
        private BtnBean btn;
        private String deadline_flag;

        public String getDeadline_flag() {
            return deadline_flag;
        }

        public void setDeadline_flag(String deadline_flag) {
            this.deadline_flag = deadline_flag;
        }

        public String getDrawing_id() {
            return drawing_id;
        }

        public void setDrawing_id(String drawing_id) {
            this.drawing_id = drawing_id;
        }

        public String getApply_user_id() {
            return apply_user_id;
        }

        public void setApply_user_id(String apply_user_id) {
            this.apply_user_id = apply_user_id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getDefect_no() {
            return defect_no;
        }

        public void setDefect_no(String defect_no) {
            this.defect_no = defect_no;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCheck_id() {
            return check_id;
        }

        public void setCheck_id(String check_id) {
            this.check_id = check_id;
        }

        public String getDrawing_position() {
            return drawing_position;
        }

        public void setDrawing_position(String drawing_position) {
            this.drawing_position = drawing_position;
        }

        public String getFlow_node() {
            return flow_node;
        }

        public void setFlow_node(String flow_node) {
            this.flow_node = flow_node;
        }

        public String getSecondary_region() {
            return secondary_region;
        }

        public void setSecondary_region(String secondary_region) {
            this.secondary_region = secondary_region;
        }

        public String getDiscipline_id() {
            return discipline_id;
        }

        public void setDiscipline_id(String discipline_id) {
            this.discipline_id = discipline_id;
        }

        public List<ActionBean> getAction() {
            return action;
        }

        public void setAction(List<ActionBean> action) {
            this.action = action;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public String getDrawing_pic_path() {
            return drawing_pic_path;
        }

        public void setDrawing_pic_path(String drawing_pic_path) {
            this.drawing_pic_path = drawing_pic_path;
        }

        public String getContractor_name() {
            return contractor_name;
        }

        public void setContractor_name(String contractor_name) {
            this.contractor_name = contractor_name;
        }

        public String getDeadline_time() {
            return deadline_time;
        }

        public void setDeadline_time(String deadline_time) {
            this.deadline_time = deadline_time;
        }

        public String getSub_con() {
            return sub_con;
        }

        public void setSub_con(String sub_con) {
            this.sub_con = sub_con;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }

        public String getBlock() {
            return block;
        }

        public void setBlock(String block) {
            this.block = block;
        }

        public String getProgram_name() {
            return program_name;
        }

        public void setProgram_name(String program_name) {
            this.program_name = program_name;
        }

        public String getTrade() {
            return trade;
        }

        public void setTrade(String trade) {
            this.trade = trade;
        }

        public String getTrade_id() {
            return trade_id;
        }

        public void setTrade_id(String trade_id) {
            this.trade_id = trade_id;
        }

        public String getUrgent_flag() {
            return urgent_flag;
        }

        public void setUrgent_flag(String urgent_flag) {
            this.urgent_flag = urgent_flag;
        }

        public String getDiscipline() {
            return discipline;
        }

        public void setDiscipline(String discipline) {
            this.discipline = discipline;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public BtnBean getBtn() {
            return btn;
        }

        public void setBtn(BtnBean btn) {
            this.btn = btn;
        }

        public static class BtnBean {
            private String save;
            private String submit;

            public String getSave() {
                return save;
            }

            public void setSave(String save) {
                this.save = save;
            }

            public String getSubmit() {
                return submit;
            }

            public void setSubmit(String submit) {
                this.submit = submit;
            }
        }

        public static class ActionBean {
            private String node_name;
            private String name_en;
            private String node_id;
            private String name;
            private String action_type;

            public String getNode_name() {
                return node_name;
            }

            public void setNode_name(String node_name) {
                this.node_name = node_name;
            }

            public String getName_en() {
                return name_en;
            }

            public void setName_en(String name_en) {
                this.name_en = name_en;
            }

            public String getNode_id() {
                return node_id;
            }

            public void setNode_id(String node_id) {
                this.node_id = node_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAction_type() {
                return action_type;
            }

            public void setAction_type(String action_type) {
                this.action_type = action_type;
            }
        }

        public static class DetailBean {
            private Integer step;
            private String user_name;
            private String user_id;
            private String pic;
            private String contractor_name;
            private String deal_type;
            private String face_img;
            private String deal_name;
            private String record_time;
            private String remarks;

            public Integer getStep() {
                return step;
            }

            public void setStep(Integer step) {
                this.step = step;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getContractor_name() {
                return contractor_name;
            }

            public void setContractor_name(String contractor_name) {
                this.contractor_name = contractor_name;
            }

            public String getDeal_type() {
                return deal_type;
            }

            public void setDeal_type(String deal_type) {
                this.deal_type = deal_type;
            }

            public String getFace_img() {
                return face_img;
            }

            public void setFace_img(String face_img) {
                this.face_img = face_img;
            }

            public String getDeal_name() {
                return deal_name;
            }

            public void setDeal_name(String deal_name) {
                this.deal_name = deal_name;
            }

            public String getRecord_time() {
                return record_time;
            }

            public void setRecord_time(String record_time) {
                this.record_time = record_time;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }
        }
    }
}
