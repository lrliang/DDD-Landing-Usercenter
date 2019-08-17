const CLIENT_ID = process.env.CLIENT_ID || '0oamx43zbrN4gI8qx0h7';
const ISSUER = process.env.ISSUER || 'https://dev-494119.oktapreview.com/oauth2/default';

export default {
  oidc: {
    clientId: CLIENT_ID,
    issuer: ISSUER,
    redirectUri: 'http://120.79.48.68/user-center/implicit',
    scope: 'openid profile email',
  },
  resourceServer: {
    messageUrl: 'http://120.79.48.68/api/messages',
  },
};
