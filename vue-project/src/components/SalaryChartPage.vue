<template>
  <el-container>
    <el-header style="text-align: left; font-size: 12px">
      <span>工资图表</span>
    </el-header>

    <el-main>
      <div id="userChart" style="height: 96%;"></div>
    </el-main>

  </el-container>
</template>
<script>
  import {findUserSalary} from "@/service/salaryManager";
  import {Message} from 'element-ui';
  import echarts from 'echarts';
  // let echarts = require("echarts/lib/echarts");
  // require("echarts/lib/chart/line");
  // require('echarts/lib/component/tooltip');

  export default {
    name: "SalaryChartVue",
    data() {
      return {
        series: [],
        legend: [],
        opt: {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#6a7985'
              }
            }
          },
          xAxis: {
            type: 'category',
            data: []
          },
          yAxis: {
            type: 'value'
          },
          series: [{
            name: '工资',
            data: [],
            type: 'line'
          }]
        },
        chart: {},
      };
    },
    beforeCreate() {
    },
    created() {
      console.log("test");
    },
    mounted() {
      this.chart = echarts.init(document.getElementById("userChart"));
      this.init();
    },
    activated() {

    },
    methods: {
      init() {
        this.getList();
      },
      getList() {
        return new Promise(resolve => {
          let param = {num:12};
          findUserSalary(param).then((data) => {
            let list = data.data;
            for (let index in list) {
              let view = list[index];
              this.opt.xAxis.data.push(view.dt);
              this.opt.series[0].data.push(view.salary);
            }
            console.log(this.opt);
            this.chart.setOption(this.opt);
          });
        });
      }
    }
  }
</script>
<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
  }

  .zZindex {
    z-index:3000 !important;
  }

</style>
