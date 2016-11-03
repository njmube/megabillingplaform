(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_destruction_certificateDetailController', Com_destruction_certificateDetailController);

    Com_destruction_certificateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_destruction_certificate', 'Cfdi', 'C_type_series'];

    function Com_destruction_certificateDetailController($scope, $rootScope, $stateParams, entity, Com_destruction_certificate, Cfdi, C_type_series) {
        var vm = this;
        vm.com_destruction_certificate = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_destruction_certificateUpdate', function(event, result) {
            vm.com_destruction_certificate = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
