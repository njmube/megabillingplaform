(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_custom_unitDetailController', Com_custom_unitDetailController);

    Com_custom_unitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_custom_unit'];

    function Com_custom_unitDetailController($scope, $rootScope, $stateParams, entity, Com_custom_unit) {
        var vm = this;
        vm.com_custom_unit = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_custom_unitUpdate', function(event, result) {
            vm.com_custom_unit = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
