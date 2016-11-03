(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_pficDetailController', Com_pficDetailController);

    Com_pficDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_pfic', 'Cfdi'];

    function Com_pficDetailController($scope, $rootScope, $stateParams, entity, Com_pfic, Cfdi) {
        var vm = this;
        vm.com_pfic = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_pficUpdate', function(event, result) {
            vm.com_pfic = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
