(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_mail_accountsDetailController', Taxpayer_mail_accountsDetailController);

    Taxpayer_mail_accountsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Taxpayer_mail_accounts', 'Taxpayer_account'];

    function Taxpayer_mail_accountsDetailController($scope, $rootScope, $stateParams, entity, Taxpayer_mail_accounts, Taxpayer_account) {
        var vm = this;

        vm.taxpayer_mail_accounts = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_mail_accountsUpdate', function(event, result) {
            vm.taxpayer_mail_accounts = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
