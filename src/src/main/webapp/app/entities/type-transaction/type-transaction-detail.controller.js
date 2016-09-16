(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Type_transactionDetailController', Type_transactionDetailController);

    Type_transactionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Type_transaction'];

    function Type_transactionDetailController($scope, $rootScope, $stateParams, entity, Type_transaction) {
        var vm = this;

        vm.type_transaction = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:type_transactionUpdate', function(event, result) {
            vm.type_transaction = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
