(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Billing_account_stateDetailController', Billing_account_stateDetailController);

    Billing_account_stateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Billing_account_state'];

    function Billing_account_stateDetailController($scope, $rootScope, $stateParams, entity, Billing_account_state) {
        var vm = this;
        vm.billing_account_state = entity;
        vm.load = function (id) {
            Billing_account_state.get({id: id}, function(result) {
                vm.billing_account_state = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:billing_account_stateUpdate', function(event, result) {
            vm.billing_account_state = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
