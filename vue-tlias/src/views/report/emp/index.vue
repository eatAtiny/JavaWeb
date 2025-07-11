<script setup>
import { onMounted } from 'vue';
import * as echarts from 'echarts';
import { empJobDataApi, empGenderApi } from '@/api/report';
import { ref } from 'vue';
import { ElMessage } from 'element-plus';

const empJobCategory = ref([]);
const empJobData = ref([]);
const empGenderData = ref([]);

onMounted(async () => {
  const result1 = await empJobDataApi();
  if (result1.data){
    empJobCategory.value = result1.data.jobList
    empJobData.value = result1.data.dataList
  } else {
    empJobCategory.value = [];
    empJobData.value = [];
    ElMessage.error('获取数据失败');
  }

  // 基于准备好的dom，初始化echarts实例
  var myChart = echarts.init(document.getElementById('main'));

  // 指定图表的配置项和数据
  var option = {
    xAxis: {
      type: 'category',
      data: empJobCategory.value
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: empJobData.value,
        type: 'bar'
      }
    ]
  };

  // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

  // 员工性别统计

  const result2 = await empGenderApi();
  if (result2.data){
    empGenderData.value = result2.data
  } else {
    empGenderData.value = [];
    ElMessage.error('获取数据失败');
  }
  
  // 基于准备好的dom，初始化echarts实例
  var myChart2 = echarts.init(document.getElementById('main2'));

  // 指定图表的配置项和数据
  var option2 = {
      title: {
        text: '员工性别',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: '50%',
          data: empGenderData.value,
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };

      // 使用刚指定的配置项和数据显示图表。
        myChart2.setOption(option2);
});



</script>

<template>
  <div class="report-container">
    <h1>员工信息统计</h1>
    <div class="charts-wrapper">
      <div class="chart-item">
        <div id="main" class="chart"></div>
      </div>
      <div class="separator"></div>
      <div class="chart-item">
        <div id="main2" class="chart"></div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.report-container {
  padding: 20px;
}

.charts-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 20px;
}

.chart-item {
  flex: 1;
}

.chart {
  width: 100%;
  height: 400px;
}

.separator {
  width: 1px;
  height: 400px;
  background-color: #ccc;
}
</style>