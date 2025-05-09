<template>
  <div id="app">
    <router-view/>
    <theme-picker/>
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker";

export default {
  name: "App",
  components: {ThemePicker},
  metaInfo() {
    return {
      title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
      titleTemplate: title => {
        return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
      }
    }
  }
};
</script>
<style>
#app .theme-picker {
  display: none;
}
.el-dialog__header {
  box-shadow: inset 0 -1px 0 #f5f5f5 !important;
}
.banner {
  width: 1200px;
  margin: 0 auto;
}
</style>
