(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_systemDetailController', C_systemDetailController);

    C_systemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_system'];

    function C_systemDetailController($scope, $rootScope, $stateParams, entity, C_system) {
        var vm = this;

        vm.c_system = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_systemUpdate', function(event, result) {
            vm.c_system = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
