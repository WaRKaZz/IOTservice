<nav class="navbar navbar-expand-md navbar-light bg-light fixed-top">
    <a href="#" class="navbar-brand">IOTService</a>
        <ul class="nav navbar-nav">
        <li class="nav-item">
            <a class="nav-link" href="/">My home</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/devices">Devices</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/settings">Settings</a>
        </li>
    </ul>
    <ul class="nav navbar-nav ml-auto">
    	<li class="nav-item">
            <a class="nav-link" href="/profile">${sessionScope.user.userFirstName} ${sessionScope.user.userLastName}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/logout">Logout</a>
        </li>
    </ul>
</nav>