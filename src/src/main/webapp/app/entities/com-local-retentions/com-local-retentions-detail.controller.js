(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_local_retentionsDetailController', Com_local_retentionsDetailController);

    Com_local_retentionsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_local_retentions', 'Com_local_taxes'];

    function Com_local_retentionsDetailController($scope, $rootScope, $stateParams, entity, Com_local_retentions, Com_local_taxes) {
        var vm = this;
        vm.com_local_retentions = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_local_retentionsUpdate', function(event, result) {
            vm.com_local_retentions = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
