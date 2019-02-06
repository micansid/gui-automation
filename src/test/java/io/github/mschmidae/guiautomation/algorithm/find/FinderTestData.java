package io.github.mschmidae.guiautomation.algorithm.find;

import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.image.Image;
import io.github.mschmidae.guiautomation.util.image.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum FinderTestData {
    SCREEN("intellij_commit_changes.png", new Position(0, 0)),
    BUTTON_COMMIT("intellij_button_commit.png", new Position(485, 787)),
    BUTTON_CANCEL("intellij_button_cancel.png", new Position(599, 787)),
    BUTTON_HELP("intellij_button_help.png", new Position(683, 787)),
    BUTTON_FRAME("intellij_button_frame.png", new Position(599, 787), new Position(683, 787)),
    CHECKBOX_UNCHECKED("intellij_checkbox_unchecked.png", new Position(474, 106), new Position(474, 129), new Position(474, 152), new Position(472, 224), new Position(472, 248), new Position(472, 272), new Position(472, 344), new Position(472, 368), new Position(472, 392), new Position(472, 416), new Position(472, 440)),
    CHECKBOX_CHECKED("intellij_checkbox_checked.png", new Position(472, 296), new Position(472, 320), new Position(472, 567));

    private final Image image;
    private final List<Position> positions = new ArrayList<>();

    FinderTestData(final String imagePath, Position... positions) {
        image = new ImageLoader().load(imagePath).orElseThrow(() -> new RuntimeException("The image " + imagePath + " could not be loaded"));
        this.positions.addAll(Arrays.asList(positions));
    }

    public Image getImage() {
        return image;
    }

    public List<Position> getPositions() {
        return Collections.unmodifiableList(positions);
    }
}
