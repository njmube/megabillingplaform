(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Package_transactionsDetailController', Package_transactionsDetailController);

    Package_transactionsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Package_transactions', 'Package_state'];

    function Package_transactionsDetailController($scope, $rootScope, $stateParams, entity, Package_transactions, Package_state) {
        var vm = this;
        vm.package_transactions = entity;
        vm.load = function (id) {
            Package_transactions.get({id: id}, function(result) {
                vm.package_transactions = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:package_transactionsUpdate', function(event, result) {
            vm.package_transactions = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
