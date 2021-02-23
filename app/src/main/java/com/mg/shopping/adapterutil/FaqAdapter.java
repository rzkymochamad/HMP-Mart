package com.mg.shopping.adapterutil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mg.shopping.R;
import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.specificproductquestionsutil.Answer;
import com.mg.shopping.jsonutil.specificproductquestionsutil.ListOfDatum;
import com.mg.shopping.objectutil.EmptyObject;
import com.mg.shopping.objectutil.ProgressObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;
import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class FaqAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_DATA_VIEW = 1;
    private static final int PROGRESS_VIEW = 2;
    private static final int ANSWER_VIEW = 3;
    private static final int QUESTION_VIEW = 4;

    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private SelectionInterface selectionInterface;

    public FaqAdapter(Context context, List<Object> dataArray) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;


    }

    public FaqAdapter(Context context, List<Object> dataArray, SelectionInterface selectionInterface) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;
        this.selectionInterface = selectionInterface;


    }

    @Override
    public int getItemViewType(int position) {

         if (dataArray.get(position) instanceof Answer) {
            return ANSWER_VIEW;
         }
         else if (dataArray.get(position) instanceof ListOfDatum) {
             return QUESTION_VIEW;
         }
         else if (dataArray.get(position) instanceof ProgressObject) {
             return PROGRESS_VIEW;
         }
         else {
             return NO_DATA_VIEW;
         }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;

        switch (viewType) {
            case NO_DATA_VIEW:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
                return  new EmptyHolder(view);

            case PROGRESS_VIEW:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
                return new ProgressHolder(view);

            case ANSWER_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.answer_item_layout, parent, false);
                return new AnswerHolder(view);

            case QUESTION_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.question_answer_item_layout, parent, false);
                return new FaqHolder(view);

            default:
                return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ((StrategyAdapter) holder).executeHolderFunctionality(holder, position);

    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }

    public FaqAdapter setSelectionInterface(SelectionInterface selectionInterface) {
        this.selectionInterface = selectionInterface;
        return this;
    }

    protected class EmptyHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private ImageView imageIcon;
        private TextView txtTitle;
        private TextView txtDescription;

        public EmptyHolder(View view) {
            super(view);

            imageIcon = (ImageView) view.findViewById(R.id.image_icon);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtDescription = (TextView) view.findViewById(R.id.txt_description);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyObject emptyState = (EmptyObject) dataArray.get(position);

            emptyHolder.imageIcon.setImageResource(emptyState.getPlaceHolderIcon());
            emptyHolder.txtTitle.setText(emptyState.getTitle());
            emptyHolder.txtDescription.setText(emptyState.getDescription());

        }


        @Override
        public int getViewTypeIdentifier() {
            return NO_DATA_VIEW;
        }
    }

    protected class ProgressHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        GeometricProgressView progressView;

        public ProgressHolder(View view) {
            super(view);
            progressView = (GeometricProgressView) view.findViewById(R.id.progressView);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {
            //do nothing
        }


        @Override
        public int getViewTypeIdentifier() {
            return PROGRESS_VIEW;
        }
    }

    protected class AnswerHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundedImageView imageUser;
        private TextView txtUserName;
        private TextView txtDateTime;
        private TextView txtAnswer;

        public AnswerHolder(View view) {
            super(view);

            imageUser = (RoundedImageView) view.findViewById(R.id.image_user);
            txtUserName = (TextView) view.findViewById(R.id.txt_user_name);
            txtDateTime = (TextView) view.findViewById(R.id.txt_date_time);
            txtAnswer = (TextView) view.findViewById(R.id.txt_answer);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final AnswerHolder answerHolder = (AnswerHolder) holder;
            final Answer answer = (Answer) dataArray.get(position);

            answerHolder.txtUserName.setText( !answer.getUser().isEmpty() ? answer.getUser().get(0).getName() : "null");
            answerHolder.txtDateTime.setVisibility(View.INVISIBLE);

            answerHolder.txtAnswer.setText(answer.getAnswer());
            GlideApp.with(context).load(BottomNavigationSample.getUserPicture(!answer.getUser().isEmpty()
                    ? answer.getUser().get(0).getImage()
                    : "null")).into(answerHolder.imageUser);

        }

        @Override
        public int getViewTypeIdentifier() {
            return ANSWER_VIEW;
        }
    }

    protected class FaqHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private TextView txtQuestion;
        private TextView txtAnswer;
        private TextView txtAllAnswer;
        private LinearLayout layoutAnswer;

        public FaqHolder(View view) {
            super(view);
            txtQuestion = (TextView) view.findViewById(R.id.txt_question);
            txtAnswer = (TextView) view.findViewById(R.id.txt_answer);
            txtAllAnswer = (TextView) view.findViewById(R.id.txt_all_answer);
            layoutAnswer = view.findViewById(R.id.layout_answer);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final FaqHolder faqHolder = (FaqHolder) holder;
            final ListOfDatum datum = (ListOfDatum) dataArray.get(position);

            faqHolder.txtQuestion.setText(datum.getName());

            if (!datum.getAnswer().isEmpty()) {

                faqHolder.layoutAnswer.setVisibility(View.VISIBLE);
                faqHolder.txtAnswer.setText(datum.getAnswer().get(0).getAnswer());

            }

            faqHolder.txtAllAnswer.setText(Utility.getStringFromRes(context, R.string.total)
                    + " " + datum.getAnswer().size()
                    + " " + Utility.getStringFromRes(context, R.string.answers));

            faqHolder.txtAllAnswer.setTag(position);
            faqHolder.txtAllAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) faqHolder.txtAllAnswer.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos));
                    }
                }
            });

        }

        @Override
        public int getViewTypeIdentifier() {
            return QUESTION_VIEW;
        }
    }



}