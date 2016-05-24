(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Package_stateDetailController', Package_stateDetailController);

    Package_stateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Package_state', 'Package_transactions'];

    function Package_stateDetailController($scope, $rootScope, $stateParams, entity, Package_state, Package_transactions) {
        var vm = this;
        vm.package_state = entity;
        vm.load = function (id) {
            Package_state.get({id: id}, function(result) {
                vm.package_state = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:package_stateUpdate', function(event, result) {
            vm.package_state = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
