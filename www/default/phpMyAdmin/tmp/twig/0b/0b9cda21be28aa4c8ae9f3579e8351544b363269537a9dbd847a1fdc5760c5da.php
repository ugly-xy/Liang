<?php

/* javascript/display.twig */
class __TwigTemplate_7e0b64173d3ca83b83ed901dc4563314d6410691467b0983d760a5a5d58de52e extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = false;

        $this->blocks = [
        ];
    }

    protected function doDisplay(array $context, array $blocks = [])
    {
        // line 1
        echo "<script type=\"text/javascript\">
if (typeof configInlineParams === \"undefined\" || !Array.isArray(configInlineParams)) configInlineParams = [];
configInlineParams.push(function() {
";
        // line 4
        echo twig_join_filter(($context["js_array"] ?? null), ";
");
        echo ";
});
if (typeof configScriptLoaded !== \"undefined\" && configInlineParams) loadInlineConfig();
</script>
";
    }

    public function getTemplateName()
    {
        return "javascript/display.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  24 => 4,  19 => 1,);
    }

    /** @deprecated since 1.27 (to be removed in 2.0). Use getSourceContext() instead */
    public function getSource()
    {
        @trigger_error('The '.__METHOD__.' method is deprecated since version 1.27 and will be removed in 2.0. Use getSourceContext() instead.', E_USER_DEPRECATED);

        return $this->getSourceContext()->getCode();
    }

    public function getSourceContext()
    {
        return new Twig_Source("", "javascript/display.twig", "/data/wwwroot/default/phpMyAdmin/templates/javascript/display.twig");
    }
}
