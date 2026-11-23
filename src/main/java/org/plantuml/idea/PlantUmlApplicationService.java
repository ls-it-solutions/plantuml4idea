package org.plantuml.idea;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.event.EditorEventMulticaster;
import com.intellij.openapi.editor.event.SelectionListener;
import org.plantuml.idea.preview.listener.PlantUmlCaretListener;
import org.plantuml.idea.preview.listener.PlantUmlDocumentListener;
import org.plantuml.idea.preview.listener.PlantUmlSelectionListener;

/**
 * Replaces the former {@code PlantUmlApplicationComponent}.
 * <p>
 * The service is created lazily on first access (see {@link PlantUmlStartupActivity}) and registers the
 * editor listeners with itself as parent {@link Disposable}, so they are removed automatically
 * when the service is disposed (e.g. on plugin unload).
 */
@Service(Service.Level.APP)
public final class PlantUmlApplicationService implements Disposable {
    private final DocumentListener plantUmlDocumentListener = new PlantUmlDocumentListener();
    private final CaretListener plantUmlCaretListener = new PlantUmlCaretListener();
    private final SelectionListener selectionListener = new PlantUmlSelectionListener();

    public PlantUmlApplicationService() {
        PlantUmlApplicationService.class.getClassLoader().setDefaultAssertionStatus(false);

        EditorEventMulticaster eventMulticaster = EditorFactory.getInstance().getEventMulticaster();
        eventMulticaster.addDocumentListener(plantUmlDocumentListener, this);
        eventMulticaster.addCaretListener(plantUmlCaretListener, this);
        eventMulticaster.addSelectionListener(selectionListener, this);
    }

    public static PlantUmlApplicationService getInstance() {
        return ApplicationManager.getApplication().getService(PlantUmlApplicationService.class);
    }

    @Override
    public void dispose() {
    }
}
