(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_transactionsDetailController', Taxpayer_transactionsDetailController);

    Taxpayer_transactionsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Taxpayer_transactions', 'Taxpayer_account'];

    function Taxpayer_transactionsDetailController($scope, $rootScope, $stateParams, entity, Taxpayer_transactions, Taxpayer_account) {
        var vm = this;

        vm.taxpayer_transactions = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_transactionsUpdate', function(event, result) {
            vm.taxpayer_transactions = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
