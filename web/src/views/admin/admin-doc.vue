<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-form layout="inline" :model="param">
          <a-form-item>
            <a-button type="primary" @click="handleQuery()">
              search
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="add()">
              create
            </a-button>
          </a-form-item>
        </a-form>
      </p>
      <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="level1"
          :loading="loading"
          :pagination="false"
      >
        <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar" />
        </template>
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              edit
            </a-button>
            <a-popconfirm
                title="Are you sure you want to delete this book?"
                ok-text="Yes"
                cancel-text="No"
                @confirm="del(record.id)"
            >
              <a-button type="danger">
                delete
              </a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>
  <a-modal
      title="doc table"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
    <a-form :model="doc" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="Name">
        <a-input v-model:value="doc.name" />
      </a-form-item>

      <a-form-item label="Parent Doc">
        <a-input v-model:value="doc.parent" />
        <a-select
            v-model:value="doc.parent"
            ref="select"
        >
          <a-select-option value="0">
            no
          </a-select-option>
          <a-select-option v-for="c in level1" :key="c.id" :value="c.id" :disabled="doc.id === c.id">
            {{c.name}}
          </a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';
import { message } from 'ant-design-vue';
import {Tool} from "@/util/tool";

export default defineComponent({
  name: 'AdminDoc',
  setup() {
    const param = ref();
    param.value = {};
    const docs = ref();
    const loading = ref(false);

    const columns = [
      {
        title: 'Name',
        dataIndex: 'name'
      },
      {
        title: 'Parent Doc',
        key: 'parent',
        dataIndex: 'parent'
      },
      {
        title: 'Sort',
        dataIndex: 'sort'
      },
      {
        title: 'Action',
        key: 'action',
        slots: { customRender: 'action' }
      }
    ];

    /**
     * level 1 tree, children is level 2 categorization
     * [{
     *   id: "",
     *   name: "",
     *   children: [{
     *     id: "",
     *     name: "",
     *   }]
     * }]
     *
     **/
    const level1 = ref();



    /**
     * 数据查询
     **/
    const handleQuery = () => {
      loading.value = true;
      axios.get("/doc/all").then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
            docs.value = data.content;
            console.log("original array", docs.value);

            level1.value = [];
            level1.value = Tool.arrayToTree(docs.value, 0);
            console.log("tree form", level1);
          } else {
            message.error(data.message);
          }
      });
    };

    // -------- 表单 ---------
    const doc = ref({});
    const modalVisible = ref(false);
    const modalLoading = ref(false);
    const handleModalOk = () => {
      modalLoading.value = true;
      axios.post("/doc/save", doc.value).then((response) => {
        modalLoading.value = false;
        const data = response.data; //data == CommonResp
        if (data.success) {
          modalVisible.value = false;
          //重新加载列表
          handleQuery();
        } else {
          message.error(data.message);
        }
      });
    };

    /**
     * edit
     */
    const edit = (record: any) => {
      modalVisible.value = true;
      doc.value = Tool.copy(record);
    };

    /**
     * add new doc
     */
    const add = () => {
      modalVisible.value = true;
      doc.value = {};
    }

    const del = (id: number) => {
      axios.delete("/doc/delete/" + id).then((response) => {
        const data = response.data; //data == CommonResp
        if (data.success) {
          //重新加载列表
          handleQuery();
        }
      });
    }

    onMounted(() => {
      handleQuery();
    });

    onMounted(() => {
      handleQuery();
    });

    return {
      param,
      // docs,
      level1,
      columns,
      loading,
      handleQuery,

      edit,
      add,

      doc,
      modalVisible,
      modalLoading,
      handleModalOk,

      del
    }
  }
});
</script>

<style scoped>
img {
  width: 50px;
  height: 50px;
}
</style>

