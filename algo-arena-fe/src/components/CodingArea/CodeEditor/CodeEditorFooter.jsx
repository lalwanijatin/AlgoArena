
function EditorFooter({ handleSubmit, inProgress }) {
  return (
    <div className="flex  bottom-0 z-10 w-full">
      <div className="mx-5 my-[10px] flex justify-between w-full">
        <div className="ml-auto flex items-center space-x-4">
          {inProgress && <span className="loading loading-spinner loading-sm"></span>}
          {!inProgress && 
          <>
            <button
              onClick={handleSubmit}
              className="px-3 py-1.5 font-medium items-center transition-all focus:outline-none inline-flex text-sm text-white bg-green-600 hover:bg-green-800 rounded-lg"
            >
              Submit
            </button>
          </>
          }
        </div>
      </div>
    </div>
  );
}

export default EditorFooter;
