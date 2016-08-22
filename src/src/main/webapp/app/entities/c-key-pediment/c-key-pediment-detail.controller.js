(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_key_pedimentDetailController', C_key_pedimentDetailController);

    C_key_pedimentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_key_pediment'];

    function C_key_pedimentDetailController($scope, $rootScope, $stateParams, entity, C_key_pediment) {
        var vm = this;

        vm.c_key_pediment = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_key_pedimentUpdate', function(event, result) {
            vm.c_key_pediment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
