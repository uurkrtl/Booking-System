function PageHeader({title}: Readonly<{ title: string }>) {
    return (
        <div className="py-2 text-center">
            <h2>{title}</h2>
        </div>
    );
}

export default PageHeader;