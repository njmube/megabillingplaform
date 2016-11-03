(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_local_transferedDetailController', Com_local_transferedDetailController);

    Com_local_transferedDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_local_transfered', 'Com_local_taxes'];

    function Com_local_transferedDetailController($scope, $rootScope, $stateParams, entity, Com_local_transfered, Com_local_taxes) {
        var vm = this;
        vm.com_local_transfered = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_local_transferedUpdate', function(event, result) {
            vm.com_local_transfered = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
