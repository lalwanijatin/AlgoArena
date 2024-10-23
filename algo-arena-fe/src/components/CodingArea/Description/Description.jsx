
import ReactMarkdown from 'react-markdown';

function Description({ Problem }) {
  const difficultyBadgeColor = Problem?.problem?.difficulty === 'EASY' ? `green` : Problem?.problem?.difficulty === 'MEDIUM' ? `orange` : `red`
  return (
    <div className="text-black rounded-lg m-2 mr-0 bg-opacity-100 border" style={{maxHeight:'85vh', minWidth: '400px', padding:'10px', overflowY: 'auto'}}>
      {/* TAB */}

        <div className="flex space-x-4">
            <div className="flex-1 mr-2 text-lg text-black font-medium">
                {Problem?.problem?.name}
            </div>
        </div>
        <div className="flex">
            <div className={`text-${difficultyBadgeColor}-700 bg-${difficultyBadgeColor}-700 inline-block rounded-[21px] py-3 text-xs font-medium capitalize `} style={{color:difficultyBadgeColor}}>
                {Problem?.problem?.difficulty}
            </div>
        </div>
        <div style={{ wordWrap: 'break-word', overflowWrap: 'break-word' }}>
            <ReactMarkdown>{Problem?.description}</ReactMarkdown>
        </div>
    </div>
  );
}
export default Description;
