const CLIENT_ID = process.env.CLIENT_ID || '0oamx43zbrN4gI8qx0h7';
const ISSUER = process.env.ISSUER || 'https://dev-494119.oktapreview.com/oauth2/default';

export default {
  oidc: {
    clientId: CLIENT_ID,
    issuer: ISSUER,
    redirectUri: 'http://localhost:3000/implicit/callback',
    scope: 'openid profile email',
  },
  resourceServer: {
    usersUrl: 'http://localhost:8000/api/messages',
  },
};
