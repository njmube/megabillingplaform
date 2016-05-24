(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_stateDetailController', C_stateDetailController);

    C_stateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_state', 'C_country', 'C_municipality'];

    function C_stateDetailController($scope, $rootScope, $stateParams, entity, C_state, C_country, C_municipality) {
        var vm = this;
        vm.c_state = entity;
        vm.load = function (id) {
            C_state.get({id: id}, function(result) {
                vm.c_state = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_stateUpdate', function(event, result) {
            vm.c_state = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
