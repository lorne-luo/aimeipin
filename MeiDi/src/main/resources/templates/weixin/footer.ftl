<div class="btfix clearfix itemsbtnbox fs20">
    <a href="${PATH}/index" class="fl <#if pageActive?has_content && pageActive == 'index'>active</#if>">
        <span class="shouyeicon"></span>
        <p>首页</p>
    </a>
    <a href="${PATH}/class" class="fl <#if pageActive?has_content && pageActive == 'class'>active</#if>">
        <span class="fenleicon"></span>
        <p>分类</p>
    </a>
    <a href="${PATH}/consult" class="fl <#if pageActive?has_content && pageActive == 'consult'>active</#if>">
        <span class="zixunicon"></span>
        <p>咨询</p>
    </a>
    <a href="${PATH}/my" class="fl <#if pageActive?has_content && pageActive == 'my'>active</#if>">
        <span class="myicon"></span>
        <p>我的</p>
    </a>
</div>