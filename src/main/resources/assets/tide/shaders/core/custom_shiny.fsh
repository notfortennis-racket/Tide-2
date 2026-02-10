#version 150

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform vec4 BaseSpriteUV;
uniform vec4 ShinySpriteUV;

in vec2 texCoord0;
out vec4 fragColor;

vec2 baseLocalUV(vec2 atlasUV) {
    return (atlasUV - BaseSpriteUV.xy) / (BaseSpriteUV.zw - BaseSpriteUV.xy);
}

vec2 shinyAtlasUV(vec2 localUV) {
    return mix(ShinySpriteUV.xy, ShinySpriteUV.zw, localUV);
}

void main() {
    vec4 originalColor = texture(Sampler0, texCoord0) * ColorModulator;
    if (originalColor.a < 0.1) discard;
    vec2 shinyTexCoord = shinyAtlasUV(baseLocalUV(texCoord0));
    vec4 color = texture(Sampler0, shinyTexCoord) * ColorModulator;
    fragColor = color;
}