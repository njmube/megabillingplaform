(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_accountIndexController', Taxpayer_accountIndexController);

    Taxpayer_accountIndexController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Taxpayer_account', 'Tax_address', 'Taxpayer_certificate', 'Type_taxpayer', 'Tax_regime', 'User'];

    function Taxpayer_accountIndexController($scope, $rootScope, $stateParams, entity, Taxpayer_account, Tax_address, Taxpayer_certificate, Type_taxpayer, Tax_regime, User) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.taxpayer_accounts = Taxpayer_account.query();

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_accountUpdate', function(event, result) {
            vm.taxpayer_account = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
