/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.ws.rs.ProcessingException
 *  javax.ws.rs.client.Client
 *  javax.ws.rs.client.Invocation$Builder
 */
package 83nnfii93jksoiow9;

import 83nnfii93jksoiow9.ForeignKey;
import 83nnfii93jksoiow9.RequestBuilder;
import 83nnfii93jksoiow9.LraRequest;
import 83nnfii93jksoiow9.ICache;
import 83nnfii93jksoiow9.RelationshipField;
import 83nnfii93jksoiow9.RelationshipModel;
import 83nnfii93jksoiow9.BaseModel;
import 83nnfii93jksoiow9.ResponseModel;
import 83nnfii93jksoiow9.ResourcePath;
import 83nnfii93jksoiow9.ExcludeField;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;

public class ResourceLoader {
    private static final String INavigationControlListener = "filter";
    private static final String LraControl = "limit";
    private static final String NavigationControl = "offset";
    private static final String GenericSearchController = "order";
    private static Client GenericSearchField = null;
    private static ExecutorService GenericSearchPanel = Executors.newCachedThreadPool();
    private final ICache<String, ResponseModel<? extends BaseModel>> SearchItem;
    private String SearchItemPanel;

    public ResourceLoader() {
        this(null);
    }

    public String INavigationControlListener() {
        return this.SearchItemPanel;
    }

    public void INavigationControlListener(String fixedResourcePath) {
        this.SearchItemPanel = fixedResourcePath;
    }

    public ResourceLoader(ICache<String, ResponseModel<? extends BaseModel>> cache) {
        this.SearchItem = cache;
    }

    public <T extends BaseModel> ResponseModel<T> INavigationControlListener(Class<T> modelClass) throws InstantiationException, IllegalAccessException, KeyManagementException, NoSuchAlgorithmException {
        return this.INavigationControlListener(modelClass, 0);
    }

    public <T extends BaseModel> ResponseModel<T> INavigationControlListener(Class<T> modelClass, int limit) throws InstantiationException, IllegalAccessException, KeyManagementException, NoSuchAlgorithmException {
        return this.INavigationControlListener(modelClass, 0, limit);
    }

    public <T extends BaseModel> ResponseModel<T> INavigationControlListener(Class<T> modelClass, int offset, int limit) throws InstantiationException, IllegalAccessException, KeyManagementException, NoSuchAlgorithmException {
        return this.INavigationControlListener(modelClass, new HashMap<String, String>(0), null, offset, limit);
    }

    public <T extends BaseModel> ResponseModel<T> INavigationControlListener(Class<T> modelClass, HashMap<String, String> filter, String order, int offset, int limit) throws InstantiationException, IllegalAccessException, KeyManagementException, NoSuchAlgorithmException {
        return this.INavigationControlListener(modelClass, this.INavigationControlListener(filter, "'"), order, offset, limit);
    }

    public <T extends BaseModel> ResponseModel<T> INavigationControlListener(Class<T> modelClass, String filter, String order, int offset, int limit) throws InstantiationException, IllegalAccessException, KeyManagementException, NoSuchAlgorithmException {
        try {
            BaseModel model = (BaseModel)modelClass.newInstance();
            String resourcePath = this.SearchItemPanel;
            if (this.SearchItemPanel == null || this.SearchItemPanel.isEmpty()) {
                resourcePath = this.NavigationControl(modelClass);
            }
            RequestBuilder requestBuilder = LraRequest.INavigationControlListener().INavigationControlListener(GenericSearchField).INavigationControlListener(resourcePath);
            if (!filter.isEmpty()) {
                requestBuilder.INavigationControlListener(INavigationControlListener, filter.replace("%", "%25"));
            }
            if (order != null && !order.isEmpty()) {
                requestBuilder.INavigationControlListener(GenericSearchController, order);
            }
            if (offset > 0) {
                requestBuilder.INavigationControlListener(NavigationControl, Integer.toString(offset));
            }
            if (limit > 0) {
                requestBuilder.INavigationControlListener(LraControl, Integer.toString(limit));
            }
            Invocation.Builder request = requestBuilder.NavigationControl();
            Type typeToken = model.GenericSearchPanel();
            if (this.SearchItem != null && this.SearchItem.INavigationControlListener(requestBuilder.GenericSearchPanel().toString())) {
                return this.SearchItem.LraControl(requestBuilder.GenericSearchPanel().toString()).get();
            }
            Future responseFuture = GenericSearchPanel.submit(new INavigationControlListener(request, typeToken));
            if (this.SearchItem != null) {
                this.SearchItem.INavigationControlListener(requestBuilder.GenericSearchPanel().toString(), responseFuture);
            }
            GenericSearchField = requestBuilder.GenericSearchController();
            return (ResponseModel)responseFuture.get();
        }
        catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ResourceLoader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void LraControl() {
        if (GenericSearchField != null) {
            GenericSearchField.close();
        }
    }

    public <T extends BaseModel> void INavigationControlListener(List<T> modelList) {
        this.INavigationControlListener((T)((Object)modelList), 1);
    }

    public <T extends BaseModel> void INavigationControlListener(List<T> modelList, int depth) {
        for (BaseModel model : modelList) {
            this.INavigationControlListener(model, depth);
        }
    }

    public <T extends BaseModel> void INavigationControlListener(T model) {
        this.INavigationControlListener(model, 1);
    }

    public <T extends BaseModel> void INavigationControlListener(T model, int depth) {
        if (depth <= 0) {
            return;
        }
        ArrayList<RelationshipModel> relationships = this.NavigationControl(model);
        for (RelationshipModel relationship : relationships) {
            Field field = relationship.LraControl();
            try {
                BaseModel relationshipModel = (BaseModel)field.getType().newInstance();
                relationshipModel.INavigationControlListener(relationship.NavigationControl());
                HashMap<String, String> filterMap = ResourceLoader.LraControl(relationshipModel);
                ResponseModel<?> responseModel = this.INavigationControlListener(relationshipModel.getClass(), filterMap, null, 0, 0);
                if (responseModel == null || responseModel.INavigationControlListener() == null || responseModel.INavigationControlListener().size() <= 0) continue;
                field.setAccessible(true);
                if (field.getType().isArray()) {
                    field.set(model, responseModel.INavigationControlListener().toArray());
                    this.INavigationControlListener((T)((Object)responseModel.INavigationControlListener()), depth - 1);
                    continue;
                }
                if (field.getType().isAssignableFrom(ArrayList.class)) {
                    field.set(model, responseModel.INavigationControlListener());
                    this.INavigationControlListener((T)((Object)responseModel.INavigationControlListener()), depth - 1);
                    continue;
                }
                field.set(model, responseModel.INavigationControlListener().get(0));
                this.INavigationControlListener((BaseModel)responseModel.INavigationControlListener().get(0), depth - 1);
            }
            catch (IllegalAccessException | InstantiationException | KeyManagementException | NoSuchAlgorithmException ex) {
                Logger.getLogger(ResourceLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static <T extends BaseModel> HashMap<String, String> LraControl(T model) {
        HashMap<String, String> filterMap = new HashMap<String, String>(0);
        Class<?> modelClass = model.getClass();
        ArrayList<Field> declaredFields = ResourceLoader.LraControl(modelClass);
        for (Field field : declaredFields) {
            if (field.getAnnotation(ExcludeField.class) != null) continue;
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                String value;
                Object fieldValue = field.get(model);
                if (fieldValue == null || (value = fieldValue.toString()).isEmpty()) continue;
                SerializedName serializedName = field.getAnnotation(SerializedName.class);
                String name = field.getName();
                if (serializedName != null) {
                    name = serializedName.value();
                }
                filterMap.put(name, value);
            }
            catch (IllegalAccessException | IllegalArgumentException ex) {
                Logger.getLogger(ResourceLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return filterMap;
    }

    public static ArrayList<Field> LraControl(Class<?> model) {
        return ResourceLoader.INavigationControlListener(model, new ArrayList<Field>(0));
    }

    private static ArrayList<Field> INavigationControlListener(Class<?> model, ArrayList<Field> fields) {
        Class<?> superclass = model.getSuperclass();
        if (superclass != null) {
            ResourceLoader.INavigationControlListener(superclass, fields);
        }
        fields.addAll(Arrays.asList(model.getDeclaredFields()));
        return fields;
    }

    private String INavigationControlListener(HashMap<String, String> map, String inclose) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder(0);
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            builder.append(key).append("=").append(inclose).append(map.get(key)).append(inclose);
            if (!it.hasNext()) continue;
            builder.append(" AND ");
        }
        return builder.toString();
    }

    private String NavigationControl(Class<? extends BaseModel> model) {
        ResourcePath resourcePath = model.getAnnotation(ResourcePath.class);
        if (resourcePath != null) {
            return resourcePath.INavigationControlListener();
        }
        return "";
    }

    private ArrayList<RelationshipModel> NavigationControl(BaseModel model) {
        Field[] fields = model.getClass().getDeclaredFields();
        ArrayList<RelationshipModel> relationships = new ArrayList<RelationshipModel>(0);
        for (Field field : fields) {
            ForeignKey foreignKey = field.getAnnotation(ForeignKey.class);
            if (foreignKey == null) continue;
            try {
                Object value;
                Field foreignKeyField = this.INavigationControlListener(fields, foreignKey.LraControl());
                if (foreignKeyField == null) continue;
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                if (field.getType() != Integer.class || (value = field.get(model)) == null) continue;
                int id = (Integer)field.get(model);
                RelationshipModel relationshipModel = new RelationshipModel(foreignKey, foreignKeyField, id);
                relationships.add(relationshipModel);
            }
            catch (IllegalAccessException | IllegalArgumentException | SecurityException ex) {
                Logger.getLogger(ResourceLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return relationships;
    }

    private Field INavigationControlListener(Field[] fields, String relationshipName) {
        for (Field field : fields) {
            RelationshipField relationshipField = field.getAnnotation(RelationshipField.class);
            if (relationshipField == null || !relationshipField.INavigationControlListener().equals(relationshipName)) continue;
            return field;
        }
        return null;
    }

    private static class INavigationControlListener<T extends BaseModel>
    implements Callable<ResponseModel<T>> {
        private final int INavigationControlListener = 3;
        private final Invocation.Builder LraControl;
        private final Type NavigationControl;

        public INavigationControlListener(Invocation.Builder request, Type typeToken) {
            this.LraControl = request;
            this.NavigationControl = typeToken;
        }

        public ResponseModel<T> INavigationControlListener() throws Exception {
            for (int i = 0; i < 3; ++i) {
                try {
                    String response = (String)this.LraControl.get(String.class);
                    return (ResponseModel)new Gson().fromJson(response, this.NavigationControl);
                }
                catch (ProcessingException processingException) {
                    continue;
                }
            }
            return null;
        }

        @Override
        public /* synthetic */ Object call() throws Exception {
            return this.INavigationControlListener();
        }
    }
}

