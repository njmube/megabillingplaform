(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_complementDetailController', C_complementDetailController);

    C_complementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_complement'];

    function C_complementDetailController($scope, $rootScope, $stateParams, entity, C_complement) {
        var vm = this;

        vm.c_complement = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_complementUpdate', function(event, result) {
            vm.c_complement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
