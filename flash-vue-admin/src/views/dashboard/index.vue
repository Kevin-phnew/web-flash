<template>
  <div class="dashboard-container">
    <div class="dashboard-text">欢迎光临:{{name}} </div>

    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="box-card">
          <el-col :span="24">
            <svg-icon  icon-class="jurassic" ></svg-icon>
            <div class="card-panel-num">
              <a href="https://huijun.ap.qlikcloud.com/" target="_blank">SAP</a>
            </div>
          </el-col>
        </el-card>
      </el-col>

      <el-col :span="6">
            <el-card class="box-card">
              <el-col :span="24">
                <svg-icon  icon-class="chart" ></svg-icon>
                <div class="card-panel-num">
                  <a href="http://www.baidu.com" target="_blank">QLink</a>
                </div>
              </el-col>
            </el-card>
          </el-col>
    </el-row>
  </div>
</template>


<script src="./dashboard.js">
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
  .el-row{
    margin-bottom: 20px;
    height: 1000px;
    &:last-child {
      margin-bottom: 0;
    }
  }

.dashboard {

  &-container {
    padding: 15px;
    background-color: #f0f2f5;
  }
  &-text {
    font-size: 14px;
    line-height: 22px;
    padding-bottom:15px;
  }
}
.echarts{
  width: 100%;
  height: 100%;
}
.box-card{
  height:250px;
}
.chart{
  height: 350px;
}
.section{
  padding:20px;
  background-color: white;
  border: 1px solid #ebeef5;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}

.box-card > div >div > .svg-icon {
  width: 10em;
  height: 10em;
  color:#34bfa3;
}
.card-panel-text{
  padding-top:10px;
  font-size:16px;
  color:gray;
}
.card-panel-num{
  padding-top:10px;
  font-size:20px;
  font-weight: bold;
}
</style>
