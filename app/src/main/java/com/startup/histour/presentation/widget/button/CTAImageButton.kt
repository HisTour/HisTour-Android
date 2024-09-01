import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.startup.histour.R
import com.startup.histour.ui.theme.HistourTheme

data class CTAImageButtonModel(
    @StringRes val textId: Int,
    @DrawableRes val drawableId: Int,
)

@Composable
fun CTAImageButton(modifier: Modifier,model: CTAImageButtonModel, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(20.dp)

    Surface(
        shape = shape,
        modifier = modifier
            .clip(shape)
            .border(1.dp, HistourTheme.colors.gray200, shape = shape)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = true),
                onClick = onClick
            ),
        color = HistourTheme.colors.gray50
    ) {
        Row(
            modifier = Modifier
                .height(36.dp)
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = model.drawableId),
                modifier = Modifier.size(20.dp),
                contentDescription = "CTAImageIcon",
            )
            Text(
                style = HistourTheme.typography.detail2Bold,
                textAlign = TextAlign.Center,
                color = HistourTheme.colors.gray500,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = stringResource(id = model.textId),
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CTAImageButtonPreview() {
    HistourTheme {
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            CTAImageButton(
                modifier = Modifier,
                model = CTAImageButtonModel(
                    textId = R.string.title_character,
                    drawableId = R.drawable.ic_ggabi
                )
            ) {}
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}