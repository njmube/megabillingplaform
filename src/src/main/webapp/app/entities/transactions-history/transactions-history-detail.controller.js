(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Transactions_historyDetailController', Transactions_historyDetailController);

    Transactions_historyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Transactions_history', 'C_system', 'User', 'Type_transaction', 'Taxpayer_account'];

    function Transactions_historyDetailController($scope, $rootScope, $stateParams, entity, Transactions_history, C_system, User, Type_transaction, Taxpayer_account) {
        var vm = this;

        vm.transactions_history = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:transactions_historyUpdate', function(event, result) {
            vm.transactions_history = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
