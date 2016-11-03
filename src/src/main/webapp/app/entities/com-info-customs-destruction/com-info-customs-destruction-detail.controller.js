(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_info_customs_destructionDetailController', Com_info_customs_destructionDetailController);

    Com_info_customs_destructionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_info_customs_destruction', 'Com_destruction_certificate'];

    function Com_info_customs_destructionDetailController($scope, $rootScope, $stateParams, entity, Com_info_customs_destruction, Com_destruction_certificate) {
        var vm = this;
        vm.com_info_customs_destruction = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_info_customs_destructionUpdate', function(event, result) {
            vm.com_info_customs_destruction = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
