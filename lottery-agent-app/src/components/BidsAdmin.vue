<template>
    <div>
        <div>
        </div>
        <div>
            <div>投注列表：</div> 
            <div>
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane label="待处理投注" name="first">
                        <el-table :data="bids" style="width: 100%">
                            <el-table-column prop="id" label="编号">
                            </el-table-column>
                            <el-table-column  label="投注项">
                                <template slot-scope="scope">  
                                    <li v-for="(option, index) in scope.row.options" v-bind:key="index">
                                        <span v-if="index == 0 || option.item.name != scope.row.options[index -1].item.name">
                                            {{ option.item.name }}
                                        </span>
                                        {{ option.text }}
                                    </li>  
                                </template>
                            </el-table-column>
                            <el-table-column label="动作">
                                <template slot-scope="scope">  
                                    <el-button
                                    size="mini"
                                    @click="acceptBid(scope.row)">处理</el-button>

                                    <el-button
                                    size="mini"
                                    @click="refuseBid(scope.row)">拒绝</el-button>
                                </template>
                            </el-table-column>
                            <el-table-column prop="customer.tel" label="投注人">
                            </el-table-column>
                            <el-table-column prop="createTime" label="投注时间">
                            </el-table-column>
                            
                        </el-table>
                        <el-dialog
                            title="投注"
                            name="file"
                            multiple="false"
                            :visible.sync="dialogVisible"
                            width="350px"
                            :before-close="handleClose">
                            <div>
                                 <el-upload
                                    class="avatar-uploader"
                                    accept="image/*"
                                    :action="uploadImage()"
                                    :show-file-list="false"
                                    :on-success="handleAvatarSuccess"
                                    :before-upload="beforeAvatarUpload">
                                    <img v-if="imageUrl" :src="imageUrl" class="avatar">
                                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                </el-upload>
                            </div>
                        </el-dialog>
                    </el-tab-pane>
                    <el-tab-pane label="历史投注" name="second">
                        <el-table :data="bids" style="width: 100%">
                            <el-table-column prop="id" label="编号">
                            </el-table-column>
                            <el-table-column  label="投注项">
                                <template slot-scope="scope">  
                                    <li v-for="(option, index) in scope.row.options" v-bind:key="index">
                                        <span v-if="index == 0 || option.item.name != scope.row.options[index -1].item.name">
                                            {{ option.item.name }}
                                        </span>
                                        {{ option.text }}
                                    </li>  
                                </template>
                            </el-table-column>
                            <el-table-column label="投注图">
                                <template slot-scope="scope">
                                    <el-popover trigger="hover" placement="top">
                                    <img :src="showImage(scope.row.imagePath)" width="400px"/>
                                    <div slot="reference" class="name-wrapper">
                                        <el-tag size="medium">投注图</el-tag>
                                    </div>
                                    </el-popover>
                                </template>
                            </el-table-column>
                            <el-table-column prop="customer.tel" label="投注人">
                            </el-table-column>
                            <el-table-column prop="createTime" label="投注时间">
                            </el-table-column>
                            <el-table-column prop="bidTime" label="完成时间">
                            </el-table-column>
                        </el-table>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    data () {
        return {
            activeName: 'first',
            agent: {},
            dialogVisible: false,
            imageUrl: "",
            bids: [],
            bidId: "",
            status: ""
        };
    },
    methods: {
      showImage(imagePath) {
          if (imagePath != null) {
            return require("../../static/images/" + imagePath);
          }
          return "";
      },
      uploadImage() {
          return "http://localhost:8080/api/v1/agents/"+this.agent.id+"/bids/"+ this.bidId+"/files"
      },
      handleClick(tab) {
        this.status = "active";
        if (tab.name != "first") {
            this.status = "history";
        } 
        this.loadBids(this.status);
      },
      loadBids(status) {
        axios
            .get('http://localhost:8080/api/v1/agents/'+this.agent.id+'/bids?status=' + status + '&page=1&size=10')
            .then(response => {
                    this.bids = response.data.content;
                })
            .catch(function (error) { 
                console.log(error);
            });
      },
      acceptBid(row) {
          this.bidId = row.id;

          let data= {status: 'acked'};
          axios
            .patch('http://localhost:8080/api/v1/agents/'+this.agent.id+'/bids/' + row.id, data, {headers: {'Content-Type': 'application/json'}})
            .then(() => this.dialogVisible = true)
            .catch(function (error) { 
                console.log(error);
            });
      },
      refuseBid(row) {
          this.bidId = row.id;
      },
      handleClose(){
          this.dialogVisible = false;
      },
      handleAvatarSuccess(){
          this.loadBids(this.status);
          this.handleClose();
      },
      beforeAvatarUpload(){
      }
    },
    mounted () {
        let agentId = sessionStorage.getItem(global.AGENT_ID_KEY);
        axios
            .get('http://localhost:8080/api/v1/agents/' + agentId)
            .then(response => (this.agent = response.data))
            .catch(function (error) { 
                console.log(error);
            });
        axios
            .get('http://localhost:8080/api/v1/agents/'+agentId+'/bids?status=active&page=1&size=10')
            .then(response => {
                    this.bids = response.data.content;
                })
            .catch(function (error) { 
                console.log(error);
            });
    }
}
</script>

<style>

</style>